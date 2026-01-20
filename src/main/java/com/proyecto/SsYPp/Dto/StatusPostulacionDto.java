package com.proyecto.SsYPp.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.proyecto.SsYPp.Entity.StatusPostulacion}
 */
@Value
public class StatusPostulacionDto implements Serializable {
    @NotNull
    Long id;
    @NotNull
    @Size(max = 255)
    String status;
}