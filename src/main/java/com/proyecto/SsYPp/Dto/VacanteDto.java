package com.proyecto.SsYPp.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class VacanteDto implements Serializable {

    private Long idVacantes;
    private String nombrePuesto;
    private String descripcion;
    private Integer numeroPlazas;
    private Integer plazasOcupadas;
    private Boolean estatus;
    private LocalDate fechaVencimiento;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime fechaPublicacion;

    private String requisitos;

    @JsonProperty("Modalidades_idModalidad")
    private Long modalidadesIdModalidad;

    @JsonProperty("carreras_idCarrera")
    private Long carrerasIdCarrera;

    @JsonProperty("horarios_idHorario")
    private Long horariosIdHorario;

    @JsonProperty("areasDgp_idArea")
    private Long areasDgpIdArea;

    private Long usuariosIdUsuario;

    private String creadoPorNombre;
    private String areaNombre;
}
