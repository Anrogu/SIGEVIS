package com.proyecto.SsYPp.Dto;

import java.time.OffsetTime;

public record AsignacionAdminRowDto(
        Long idAsignacion,
        OffsetTime fechaInicio,
        OffsetTime fechaFin,
        String nombrePrestador,
        String nombreVacante
) {
}
