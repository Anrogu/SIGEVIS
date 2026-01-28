package com.proyecto.SsYPp.Dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record AsistenciaHistorialRowDto(
        OffsetDateTime fecha,
        OffsetDateTime horaEntrada,
        OffsetDateTime horaSalida,
        BigDecimal horas
) {}
