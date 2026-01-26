package com.proyecto.SsYPp.Dto;

import java.time.OffsetTime;

public record PostulacionAdminRowDto(
        Long id,
        OffsetTime fechaPostulacion,
        String prestadorNombre,
        String prestadorEmail,
        String vacanteNombre,
        Long estatusId,
        String estatusTexto
) {}
