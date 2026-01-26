package com.proyecto.SsYPp.Dto;

import java.time.OffsetTime;

public record PostulacionAdminDetalleDto(
        Long id,
        OffsetTime fechaPostulacion,
        String comentarios,

        Long prestadorId,
        String prestadorNombre,
        String prestadorEmail,

        Long vacanteId,
        String vacanteNombre,
        String requisitosVacante,

        Long estatusId,
        String estatusTexto
) {}
