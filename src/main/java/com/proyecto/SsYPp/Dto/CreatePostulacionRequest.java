package com.proyecto.SsYPp.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CreatePostulacionRequest implements Serializable {

    @NotNull(message = "vacanteId es obligatorio")
    private Long vacanteId;

    // Opcional (puede venir null o vacío)
    private String comentarios;
}
