package com.proyecto.SsYPp.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.time.OffsetTime;

/**
 * DTO for {@link com.proyecto.SsYPp.Entity.Vacante}
 */
@Getter
@Setter
@NoArgsConstructor
public class VacanteDto implements Serializable {
    Long idVacantes;
    String nombrePuesto;
    String descripcion;
    Integer numeroPlazas;
    Boolean estatus;
    OffsetTime fechaPublicacion;
    String requisitos;
    String carrera;
    Long AreasDgp_idArea;
    Integer Perfiles_idPerfil;
    Integer Horarios_idHorario;
    Integer Modalidades_idModalidad;
    Long Asignaciones_idAsignacion;
}