package com.proyecto.SsYPp.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.proyecto.SsYPp.Entity.Rol}
 */
@Value
public class RolDto implements Serializable {
    Long id;
    @NotNull
    @Size(max = 255)
    String nombre;
}