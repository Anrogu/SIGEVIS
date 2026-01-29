package com.proyecto.SsYPp.Repository;

import com.proyecto.SsYPp.Dto.PostulacionAdminRowDto;
import com.proyecto.SsYPp.Dto.PostulacionCoordinadorRowDto;
import com.proyecto.SsYPp.Dto.PostulacionPrestadorRowDto;
import com.proyecto.SsYPp.Dto.PostulacionAdminDetalleDto;
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

    // ✅ VALIDAR SI EL PRESTADOR YA FUE ACEPTADO (estatus = 3)
    boolean existsByUsuariosIdusuario_IdAndEstatusIdestatus_Id(Long usuarioId, Long estatusId);

    // =========================
    // COORDINADOR
    // =========================

    // Entity completo
    @Query(value = """
        SELECT p.*
        FROM "Postulaciones" p
        JOIN "Vacantes" v ON v."idVacantes" = p."Vacante_IdVacante"
        WHERE v."AreasDgp_idArea" = :areaId
        ORDER BY p."idPostulacion" DESC
    """, nativeQuery = true)
    List<Postulacion> findPostulacionesByArea(@Param("areaId") Long areaId);

    // Projection
    @Query(value = """
        SELECT
            p."idPostulacion" AS id,
            p."fechaPostulacion" AS fechaPostulacion,
            u."idusuario" AS prestadorId,
            u."email" AS prestadorEmail,
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

    // =========================
    // PRESTADOR
    // =========================

    // Vacantes ya postuladas
    @Query(value = """
        SELECT p."Vacante_IdVacante"
        FROM "Postulaciones" p
        WHERE p."Usuarios_idUsuario" = :usuarioId
    """, nativeQuery = true)
    List<Long> findVacanteIdsPostuladasByUsuario(@Param("usuarioId") Long usuarioId);

    // Listado del prestador
    @Query(value = """
        SELECT
            p."idPostulacion" AS idPostulacion,
            to_char(p."fechaPostulacion"::time, 'HH24:MI') AS fechaPostulacion,
            p."comentarios" AS comentarios,
            v."idVacantes" AS vacanteId,
            v."nombrePuesto" AS vacanteNombre,
            v."numeroPlazas" AS numeroPlazas,
            a."nombre" AS areaNombre,
            COALESCE(sp."idPostulacion", 1) AS estatusId,
            CASE
                WHEN COALESCE(sp."idPostulacion", 1) = 1 THEN 'Enviada'
                WHEN COALESCE(sp."idPostulacion", 1) = 2 THEN 'Rechazada'
                WHEN COALESCE(sp."idPostulacion", 1) = 3 THEN 'Aceptada'
                ELSE 'Enviada'
            END AS estatusTexto
        FROM "Postulaciones" p
        JOIN "Vacantes" v ON v."idVacantes" = p."Vacante_IdVacante"
        LEFT JOIN "AreasDgp" a ON a."idArea" = v."AreasDgp_idArea"
        LEFT JOIN "StatusPostulacion" sp ON sp."idPostulacion" = p."Estatus_IdEstatus"
        WHERE p."Usuarios_idUsuario" = :usuarioId
        ORDER BY p."fechaPostulacion" DESC
    """, nativeQuery = true)
    List<PostulacionPrestadorRowDto> findRowsByUsuario(@Param("usuarioId") Long usuarioId);

    // =========================
    // ADMIN
    // =========================

    @Query(value = """
        SELECT
            p."idPostulacion" AS id,
            p."fechaPostulacion" AS fechaPostulacion,
            CONCAT(
                u."nombre", ' ', u."primerapellido",
                COALESCE(NULLIF(CONCAT(' ', u."segundoapellido"), ' '), '')
            ) AS prestadorNombre,
            u."email" AS prestadorEmail,
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
        ORDER BY p."fechaPostulacion" DESC
    """, nativeQuery = true)
    List<PostulacionAdminRowDto> findAllAdminRows();

    @Query(value = """
        SELECT
            p."idPostulacion" AS id,
            p."fechaPostulacion" AS fechaPostulacion,
            p."comentarios" AS comentarios,
            u."idusuario" AS prestadorId,
            CONCAT(
                u."nombre", ' ', u."primerapellido",
                COALESCE(NULLIF(CONCAT(' ', u."segundoapellido"), ' '), '')
            ) AS prestadorNombre,
            u."email" AS prestadorEmail,
            v."idVacantes" AS vacanteId,
            v."nombrePuesto" AS vacanteNombre,
            v."requisitos" AS requisitosVacante,
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
        WHERE p."idPostulacion" = :id
        LIMIT 1
    """, nativeQuery = true)
    PostulacionAdminDetalleDto findAdminDetalleById(@Param("id") Long id);

}
