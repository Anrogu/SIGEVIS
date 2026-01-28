package com.proyecto.SsYPp.Repository;

import com.proyecto.SsYPp.Entity.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.OffsetDateTime;
import java.util.Optional;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {

    Optional<Asistencia> findFirstByUsuario_IdAndHoraSalidaIsNull(Long idUsuario);
    Optional<Asistencia> findFirstByUsuario_IdAndHoraEntradaBetween(Long usuarioId,
                                                                    OffsetDateTime inicio,
                                                                    OffsetDateTime fin);
    Optional<Asistencia> findFirstByUsuario_IdAndHoraEntradaBetweenAndHoraSalidaIsNull(Long usuarioId,
                                                                                       OffsetDateTime inicio,
                                                                                       OffsetDateTime fin);
}
