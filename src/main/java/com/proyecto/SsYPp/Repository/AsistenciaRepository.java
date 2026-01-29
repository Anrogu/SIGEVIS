package com.proyecto.SsYPp.Repository;

import com.proyecto.SsYPp.Entity.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {

    // ✅ 1) Última asistencia abierta (sin salida) del usuario
    Optional<Asistencia> findFirstByUsuario_IdAndHoraSalidaIsNullOrderByHoraEntradaDesc(Long idUsuario);

    // ✅ 2) Asistencia de HOY (cualquier estado: con o sin salida)
    // Se usa para GET /asistencias/hoy
    Optional<Asistencia> findFirstByUsuario_IdAndHoraEntradaBetweenOrderByHoraEntradaDesc(
            Long usuarioId,
            OffsetDateTime inicio,
            OffsetDateTime fin
    );

    // ✅ 3) Asistencia de HOY pendiente (entrada hoy y sin salida)
    Optional<Asistencia> findFirstByUsuario_IdAndHoraEntradaBetweenAndHoraSalidaIsNullOrderByHoraEntradaDesc(
            Long usuarioId,
            OffsetDateTime inicio,
            OffsetDateTime fin
    );
}
