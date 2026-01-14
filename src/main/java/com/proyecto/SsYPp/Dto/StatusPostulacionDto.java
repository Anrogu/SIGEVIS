package com.proyecto.SsYPp.Dto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.proyecto.SsYPp.Entity.StatusPostulacion}
 */
@Value
public class StatusPostulacionDto implements Serializable {
    Long id;
    @Size(max = 255)
    String status;
}