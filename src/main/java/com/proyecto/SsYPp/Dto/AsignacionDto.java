package com.proyecto.SsYPp.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.time.OffsetTime;
import java.util.Set;

/**
 * DTO for {@link com.proyecto.SsYPp.Entity.Asignacion}
 */
@Value
public class AsignacionDto implements Serializable {
    Long id;
    @NotNull
    OffsetTime fechaInicio;
    @NotNull
    OffsetTime fechaFin;
    @NotNull
    PostulacionDto postulacionesIdpostulacion;
    VacanteDto vacantesIdvacante;
    ActividadDto actividades;
}