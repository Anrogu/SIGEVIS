package com.proyecto.SsYPp.Service.Impl;

import com.proyecto.SsYPp.Dto.AsistenciaHoyDto; // ✅ NUEVO
import com.proyecto.SsYPp.Entity.Asistencia;
import com.proyecto.SsYPp.Entity.Contador;
import com.proyecto.SsYPp.Entity.Usuario;
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
import java.time.format.DateTimeFormatter; // ✅ NUEVO
import java.util.Optional; // ✅ NUEVO

@Service
@Transactional
public class AsistenciaServiceImpl implements AsistenciaService {

    private final AsistenciaRepository asistenciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ContadorRepository contadorRepository;

    public AsistenciaServiceImpl(
            AsistenciaRepository asistenciaRepository,
            UsuarioRepository usuarioRepository,
            ContadorRepository contadorRepository
    ) {
        this.asistenciaRepository = asistenciaRepository;
        this.usuarioRepository = usuarioRepository;
        this.contadorRepository = contadorRepository;

        System.out.println("ContadorRepository inyectado: " + contadorRepository);
    }

    @Override
    public Asistencia registrarEntrada(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // ✅ RANGO DE HOY (00:00 a 23:59:59)
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime inicio = now.toLocalDate().atStartOfDay().atOffset(now.getOffset());
        OffsetDateTime fin = inicio.plusDays(1);

        // ✅ si ya hay registro hoy (aunque ya esté cerrado), no crear otro
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

        // ✅ RANGO DE HOY (00:00 a 24:00)
        OffsetDateTime inicio = now.toLocalDate()
                .atStartOfDay()
                .atOffset(now.getOffset());

        OffsetDateTime fin = inicio.plusDays(1);

        // ✅ buscar SOLO la asistencia de HOY que esté activa (sin salida)
        Asistencia asistencia = asistenciaRepository
                .findFirstByUsuario_IdAndHoraEntradaBetweenAndHoraSalidaIsNullOrderByHoraEntradaDesc(usuarioId, inicio, fin)
                .orElseThrow(() -> new RuntimeException("No existe entrada activa hoy"));

        // ✅ registrar salida
        asistencia.setHoraSalida(now);
        asistenciaRepository.save(asistencia);

        // ✅ cálculo de horas (igual que ya lo tienes)
        Duration duracion = Duration.between(
                asistencia.getHoraEntrada(),
                asistencia.getHoraSalida()
        );

        BigDecimal horas = BigDecimal
                .valueOf(duracion.toMinutes())
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);

        // ✅ guardar contador (igual que ya lo tienes)
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

    // =========================================================
    // ✅ NUEVO: Estatus de asistencia de HOY (solo lectura)
    // =========================================================
    @Override
    public AsistenciaHoyDto obtenerEstatusHoy(Long usuarioId) {

        // (Opcional) validar que existe usuario
        usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime inicio = now.toLocalDate().atStartOfDay().atOffset(now.getOffset());
        OffsetDateTime fin = inicio.plusDays(1);

        Optional<Asistencia> opt = asistenciaRepository
                .findFirstByUsuario_IdAndHoraEntradaBetweenOrderByHoraEntradaDesc(usuarioId, inicio, fin);

        AsistenciaHoyDto dto = new AsistenciaHoyDto();

        if (opt.isEmpty()) {
            dto.setExiste(false);
            dto.setTieneEntrada(false);
            dto.setTieneSalida(false);
            dto.setHoraEntrada(null);
            dto.setHoraSalida(null);
            dto.setEstado("SIN_REGISTRO");
            return dto;
        }

        Asistencia a = opt.get();

        boolean tieneEntrada = a.getHoraEntrada() != null;
        boolean tieneSalida = a.getHoraSalida() != null;

        dto.setExiste(true);
        dto.setTieneEntrada(tieneEntrada);
        dto.setTieneSalida(tieneSalida);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");
        dto.setHoraEntrada(tieneEntrada ? a.getHoraEntrada().toLocalTime().format(fmt) : null);
        dto.setHoraSalida(tieneSalida ? a.getHoraSalida().toLocalTime().format(fmt) : null);

        if (tieneEntrada && tieneSalida) dto.setEstado("COMPLETA");
        else if (tieneEntrada) dto.setEstado("PENDIENTE_SALIDA");
        else dto.setEstado("SIN_REGISTRO");

        return dto;
    }
}
