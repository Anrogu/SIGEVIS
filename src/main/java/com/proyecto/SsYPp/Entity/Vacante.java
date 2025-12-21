package com.proyecto.SsYPp.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetTime;
@Getter
@Setter
@Entity
@Table(name = "Vacantes")
public class Vacante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVacantes;

    private String nombrePuesto;
    private String descripcion;
    private Integer numeroPlazas;
    private Boolean estatus;
    private OffsetTime fechaPublicacion;
    private String requisitos;
    private String carrera;

    // Claves Foráneas (IDs)
    private Integer Perfiles_idPerfil;
    private Long AreasDgp_idArea;
    private Integer Horarios_idHorario;
    private Integer Modalidades_idModalidad;
    private Long Asignaciones_idAsignacion;

}