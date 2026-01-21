package com.proyecto.SsYPp.Service.Impl;
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

        asistenciaRepository
                .findFirstByUsuario_IdAndHoraSalidaIsNull(usuarioId)
                .ifPresent(a -> {
                    throw new RuntimeException("El usuario ya tiene una entrada activa");
                });

        Asistencia asistencia = new Asistencia();
        asistencia.setUsuario(usuario);
        asistencia.setHoraEntrada(OffsetDateTime.now());

        return asistenciaRepository.save(asistencia);
    }

    @Override
    public Asistencia registrarSalida(Long usuarioId) {

        Asistencia asistencia = asistenciaRepository
                .findFirstByUsuario_IdAndHoraSalidaIsNull(usuarioId)
                .orElseThrow(() -> new RuntimeException("No existe entrada activa"));

        asistencia.setHoraSalida(OffsetDateTime.now());
        asistenciaRepository.save(asistencia);

        Duration duracion = Duration.between(
                asistencia.getHoraEntrada(),
                asistencia.getHoraSalida()
        );

        BigDecimal horas = BigDecimal
                .valueOf(duracion.toMinutes())
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);

        Contador contador = new Contador();
        contador.setIdusuario(asistencia.getUsuario());
        contador.setIdasistencia(asistencia);
        contador.setHorasTotales(horas);
        contador.setFecha(OffsetDateTime.now());

        contadorRepository.save(contador);

        return asistencia;
    }

    @Override
    public BigDecimal totalHoras(Long usuarioId) {
        return contadorRepository.totalHorasPorUsuario(usuarioId);
    }
}
