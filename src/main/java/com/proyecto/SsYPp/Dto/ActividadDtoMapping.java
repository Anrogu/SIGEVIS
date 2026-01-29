package com.proyecto.SsYPp.Dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ActividadDtoMapping {
    Long getIdActividad();
    String getFechaActividad(); // Se deja String si usas to_char en el SQL
    String getDescripcion();
    String getEstatusActividad();
    String getTipoActividad();
    BigDecimal getHorasDestinadas();
}