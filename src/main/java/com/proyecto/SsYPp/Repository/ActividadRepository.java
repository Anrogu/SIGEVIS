package com.proyecto.SsYPp.Repository;

import com.proyecto.SsYPp.Dto.ActividadDto;
import com.proyecto.SsYPp.Dto.ActividadDtoMapping;
import com.proyecto.SsYPp.Dto.PostulacionPrestadorRowDto;
import com.proyecto.SsYPp.Entity.Actividad;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Integer> {
    @Query(value = """
        SELECT
            p."idActividad" AS idActividad,
            p."fechaActividad" AS fechaActividad,
            p."descripcion" AS descripcion,
            p."estatusActividad" AS estatusActividad,
            p."tipoActividad" AS tipoActividad,
            p."horasDestinadas" AS horasDestinadas
        FROM "actividades" p
        WHERE p."IdUsuario" = :usuarioId
        ORDER BY p."fechaActividad" DESC
    """, nativeQuery = true)
    List<ActividadDtoMapping> findRowsByUsuario(@Param("usuarioId") Long usuarioId);
    @Transactional
    @Modifying
    @Query(value = "UPDATE actividades SET \"estatusActividad\" = :estatus WHERE \"idActividad\" = :id", nativeQuery = true)
    void updateEstatus(@Param("id") Long id, @Param("estatus") String estatus);
}
