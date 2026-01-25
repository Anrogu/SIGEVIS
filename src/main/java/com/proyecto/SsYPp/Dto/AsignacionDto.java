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
    private Long postulacionesIdpostulacion;

    @NotNull
    private Long vacantesIdvacante;

    // ✅ NUEVOS CAMPOS PARA MOSTRAR EN TABLA
    private String prestadorNombre;
    private String prestadorEmail;

    private String vacanteNombre;
    private String areaNombre;
}
