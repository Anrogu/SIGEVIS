package com.proyecto.SsYPp.Dto;

import java.time.OffsetTime;

public interface PostulacionCoordinadorRowDto {
    Long getId();
    OffsetTime getFechaPostulacion();

    Long getPrestadorId();
    String getPrestadorNombre();
    String getPrestadorEmail();

    Long getVacanteId();
    String getVacanteNombre();

    Long getEstatusId();
    String getEstatusTexto();
}
