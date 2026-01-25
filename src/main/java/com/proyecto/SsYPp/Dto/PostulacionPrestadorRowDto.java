package com.proyecto.SsYPp.Dto;

public interface PostulacionPrestadorRowDto {
    Long getIdPostulacion();

    // 👇 OJO: String porque viene de to_char(...)
    String getFechaPostulacion();

    String getComentarios();

    Long getVacanteId();
    String getVacanteNombre();
    Integer getNumeroPlazas();

    String getAreaNombre();

    Integer getEstatusId();
    String getEstatusTexto();
}
