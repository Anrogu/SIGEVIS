package com.proyecto.SsYPp.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.proyecto.SsYPp.Entity.Carrera}
 */
@Value
public class CarreraDto implements Serializable {
    Integer id;
    @NotNull
    @Size(max = 255)
    String nombre;
    @NotNull
    @Size(max = 255)
    String descripcion;
}