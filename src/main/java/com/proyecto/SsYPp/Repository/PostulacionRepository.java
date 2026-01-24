package com.proyecto.SsYPp.Repository;

import com.proyecto.SsYPp.Dto.PostulacionCoordinadorRowDto;
import com.proyecto.SsYPp.Entity.Postulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostulacionRepository extends JpaRepository<Postulacion, Long> {

    // ✅ Evitar postulación duplicada (usuario + vacante)
    boolean existsByUsuariosIdusuario_IdAndVacanteIdvacante_Id(Long usuarioId, Long vacanteId);

    // ✅ Postulaciones por área (COORDINADOR) - entity completo (NO lo quitamos)
    @Query(value = """
        SELECT p.*
        FROM "Postulaciones" p
        JOIN "Vacantes" v ON v."idVacantes" = p."Vacante_IdVacante"
        WHERE v."AreasDgp_idArea" = :areaId
        ORDER BY p."idPostulacion" DESC
    """, nativeQuery = true)
    List<Postulacion> findPostulacionesByArea(@Param("areaId") Long areaId);

    // ✅ LISTADO COORDINADOR (projection)
    @Query(value = """
        SELECT
            p."idPostulacion" AS id,
            p."fechaPostulacion" AS fechaPostulacion,

            u."idusuario" AS prestadorId,
            CONCAT(
                u."nombre", ' ', u."primerapellido",
                COALESCE(NULLIF(CONCAT(' ', u."segundoapellido"), ' '), '')
            ) AS prestadorNombre,

            v."idVacantes" AS vacanteId,
            v."nombrePuesto" AS vacanteNombre,

            COALESCE(sp."idPostulacion", 1) AS estatusId,
            CASE
            WHEN COALESCE(sp."idPostulacion", 1) = 1 THEN 'Nuevo'
            WHEN COALESCE(sp."idPostulacion", 1) = 2 THEN 'Rechazado'
            WHEN COALESCE(sp."idPostulacion", 1) = 3 THEN 'Aceptado'
            ELSE 'Nuevo'
            END AS estatusTexto
            

        FROM "Postulaciones" p
        JOIN "usuarios" u ON u."idusuario" = p."Usuarios_idUsuario"
        JOIN "Vacantes" v ON v."idVacantes" = p."Vacante_IdVacante"
        LEFT JOIN "StatusPostulacion" sp ON sp."idPostulacion" = p."Estatus_IdEstatus"
        WHERE v."AreasDgp_idArea" = :areaId
        ORDER BY p."fechaPostulacion" DESC
    """, nativeQuery = true)
    List<PostulacionCoordinadorRowDto> findRowsByArea(@Param("areaId") Long areaId);

}
