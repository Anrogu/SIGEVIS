package com.proyecto.SsYPp.Service.Impl;

import com.proyecto.SsYPp.Dto.AsistenciaHoyDto;
import com.proyecto.SsYPp.Dto.PrestadorAsistenciaRowDto; // ✅ Importante
import com.proyecto.SsYPp.Entity.Asignacion;
import com.proyecto.SsYPp.Entity.Asistencia;
import com.proyecto.SsYPp.Entity.Contador;
import com.proyecto.SsYPp.Entity.Usuario;
import com.proyecto.SsYPp.Repository.AsignacionRepository; // ✅ Importante
import com.proyecto.SsYPp.Repository.AsistenciaRepository;
import com.proyecto.SsYPp.Repository.ContadorRepository;
import com.proyecto.SsYPp.Repository.UsuarioRepository;
import com.proyecto.SsYPp.Service.AsistenciaService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
public class AsistenciaServiceImpl implements AsistenciaService {

    private final AsistenciaRepository asistenciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ContadorRepository contadorRepository;

    private final AsignacionRepository asignacionRepository;

    public AsistenciaServiceImpl(
            AsistenciaRepository asistenciaRepository,
            UsuarioRepository usuarioRepository,
            ContadorRepository contadorRepository,
            AsignacionRepository asignacionRepository
    ) {
        this.asistenciaRepository = asistenciaRepository;
        this.usuarioRepository = usuarioRepository;
        this.contadorRepository = contadorRepository;
        this.asignacionRepository = asignacionRepository;
    }

    @Override
    public Asistencia registrarEntrada(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime inicio = now.toLocalDate().atStartOfDay().atOffset(now.getOffset());
        OffsetDateTime fin = inicio.plusDays(1);

        asistenciaRepository.findFirstByUsuario_IdAndHoraEntradaBetweenOrderByHoraEntradaDesc(usuarioId, inicio, fin)
                .ifPresent(a -> { throw new RuntimeException("Ya tienes una asistencia registrada hoy"); });

        Asistencia asistencia = new Asistencia();
        asistencia.setUsuario(usuario);
        asistencia.setHoraEntrada(now);

        return asistenciaRepository.save(asistencia);
    }

    @Override
    public Asistencia registrarSalida(Long usuarioId) {
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime inicio = now.toLocalDate().atStartOfDay().atOffset(now.getOffset());
        OffsetDateTime fin = inicio.plusDays(1);

        Asistencia asistencia = asistenciaRepository
                .findFirstByUsuario_IdAndHoraEntradaBetweenAndHoraSalidaIsNullOrderByHoraEntradaDesc(usuarioId, inicio, fin)
                .orElseThrow(() -> new RuntimeException("No existe entrada activa hoy"));

        asistencia.setHoraSalida(now);
        asistenciaRepository.save(asistencia);

        Duration duracion = Duration.between(asistencia.getHoraEntrada(), asistencia.getHoraSalida());
        BigDecimal horas = BigDecimal.valueOf(duracion.toMinutes())
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);

        Contador contador = new Contador();
        contador.setIdusuario(asistencia.getUsuario());
        contador.setIdasistencia(asistencia);
        contador.setHorasTotales(horas);
        contador.setFecha(now);

        contadorRepository.save(contador);

        return asistencia;
    }

    @Override
    public BigDecimal totalHoras(Long usuarioId) {
        return contadorRepository.totalHorasPorUsuario(usuarioId);
    }

    @Override
    public AsistenciaHoyDto obtenerEstatusHoy(Long usuarioId) {
        usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime inicio = now.toLocalDate().atStartOfDay().atOffset(now.getOffset());
        OffsetDateTime fin = inicio.plusDays(1);

        Optional<Asistencia> opt = asistenciaRepository
                .findFirstByUsuario_IdAndHoraEntradaBetweenOrderByHoraEntradaDesc(usuarioId, inicio, fin);

        return mapToDto(opt);
    }

    // =========================================================
    // ✅ NUEVO: LISTA PARA EL COORDINADOR
    // =========================================================

    @Override
    public List<PrestadorAsistenciaRowDto> obtenerListaParaCoordinador(String authName) {

        // 1. Obtener al Coordinador y su Área
        Usuario coordinador = usuarioRepository.findByEmail(authName);
        if (coordinador == null || coordinador.getArea() == null) {
            return new ArrayList<>();
        }
        Long areaId = coordinador.getArea().getId();

        // 2. Buscar asignaciones
        List<Asignacion> asignaciones = asignacionRepository.findByVacantesIdvacante_AreasdgpIdarea_Id(areaId);

        // Key: ID del Usuario, Value: El DTO de la fila
        Map<Long, PrestadorAsistenciaRowDto> usuariosUnicos = new HashMap<>();

        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime inicioDia = now.toLocalDate().atStartOfDay().atOffset(now.getOffset());
        OffsetDateTime finDia = inicioDia.plusDays(1);
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");

        // 3. Recorrer asignaciones
        for (Asignacion asig : asignaciones) {
            Usuario u = asig.getUsuariosIdusuario();
            if (u == null) continue;

            // ✅ VALIDACIÓN ANTI-DUPLICADOS
            // Si ya procesamos a este usuario (id), saltamos a la siguiente asignación
            if (usuariosUnicos.containsKey(u.getId())) {
                continue;
            }

            // Buscar asistencia de HOY
            Optional<Asistencia> opt = asistenciaRepository
                    .findFirstByUsuario_IdAndHoraEntradaBetweenOrderByHoraEntradaDesc(u.getId(), inicioDia, finDia);

            // Mapear DTO
            AsistenciaHoyDto hoyDto = mapToDto(opt);

            String nombreCompleto = u.getNombre() + " " + u.getPrimerapellido() + " " + (u.getSegundoapellido() != null ? u.getSegundoapellido() : "");
            String carrera = (u.getCarrerasIdcarrera() != null) ? u.getCarrerasIdcarrera().getNombre() : "Sin carrera";

            PrestadorAsistenciaRowDto row = new PrestadorAsistenciaRowDto(
                    u.getId(),
                    nombreCompleto,
                    u.getEmail(),
                    carrera,
                    hoyDto
            );

            usuariosUnicos.put(u.getId(), row);
        }

        return new ArrayList<>(usuariosUnicos.values());
    }

    // --- Helper para no repetir código de mapeo ---
    private AsistenciaHoyDto mapToDto(Optional<Asistencia> opt) {
        AsistenciaHoyDto dto = new AsistenciaHoyDto();
        if (opt.isEmpty()) {
            dto.setExiste(false);
            dto.setTieneEntrada(false);
            dto.setTieneSalida(false);
            dto.setHoraEntrada("—");
            dto.setHoraSalida("—");
            dto.setEstado("SIN_REGISTRO");
            return dto;
        }

        Asistencia a = opt.get();
        boolean tieneEntrada = a.getHoraEntrada() != null;
        boolean tieneSalida = a.getHoraSalida() != null;
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");

        dto.setExiste(true);
        dto.setTieneEntrada(tieneEntrada);
        dto.setTieneSalida(tieneSalida);
        dto.setHoraEntrada(tieneEntrada ? a.getHoraEntrada().format(fmt) : "—");
        dto.setHoraSalida(tieneSalida ? a.getHoraSalida().format(fmt) : "—");

        if (tieneEntrada && tieneSalida) dto.setEstado("COMPLETA");
        else if (tieneEntrada) dto.setEstado("PENDIENTE_SALIDA");
        else dto.setEstado("SIN_REGISTRO");

        return dto;
    }
}