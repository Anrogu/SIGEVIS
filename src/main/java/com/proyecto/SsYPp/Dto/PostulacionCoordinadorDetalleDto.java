package com.proyecto.SsYPp.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostulacionCoordinadorDetalleDto {

    private Long id;
    private OffsetTime fechaPostulacion;

    private Long prestadorId;
    private String prestadorNombre;
    private String prestadorEmail;

    private Long vacanteId;
    private String vacanteNombre;

    private Long estatusId;
    private String estatusTexto;

    // Para “comprobación”
    private Long vacanteCarreraId;
    private String vacanteCarreraNombre;

    private Long prestadorCarreraId;
    private String prestadorCarreraNombre;

    private boolean cumpleCarrera;

    private Long vacanteModalidadId;
    private String vacanteModalidadNombre;
    private boolean cumpleModalidad;

    private Long vacanteHorarioId;
    private String vacanteHorarioNombre;
    private boolean cumpleHorario;

    private String requisitosVacante;
    private String comentarios;
}
