package com.proyecto.SsYPp.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetTime;

@Getter
@Setter
@NoArgsConstructor
public class AsignacionDto {
    private Long id;

    @NotNull
    private OffsetTime fechaInicio;

    @NotNull
    private OffsetTime fechaFin;

    @NotNull
    private Long postulacionesIdpostulacion; // Objeto para el ID

    @NotNull
    private Long vacantesIdvacante; // Según tu log, el campo se llama 'vacante'
}