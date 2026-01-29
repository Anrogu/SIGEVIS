package com.proyecto.SsYPp.Dto;

import java.time.OffsetTime;

public interface AsignacionCoordinadorRowDto {

    Long getIdAsignacion();
    OffsetTime getFechaInicio();
    OffsetTime getFechaFin();

    String getNombrePrestador();
    String getPrestadorEmail();

    String getNombreVacante();
    String getAreaNombre();
}
