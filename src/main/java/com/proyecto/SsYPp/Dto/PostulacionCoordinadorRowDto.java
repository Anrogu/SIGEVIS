package com.proyecto.SsYPp.Dto;

import java.time.OffsetTime;

public interface PostulacionCoordinadorRowDto {
    Long getId();
    OffsetTime getFechaPostulacion();

    Long getPrestadorId();
    String getPrestadorNombre();

    Long getVacanteId();
    String getVacanteNombre();

    Long getEstatusId();
    String getEstatusTexto();
}
