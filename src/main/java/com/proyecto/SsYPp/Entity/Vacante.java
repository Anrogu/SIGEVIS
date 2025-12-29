package com.proyecto.SsYPp.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "\"Vacantes\"")
public class Vacante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idVacantes\"", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "\"nombrePuesto\"", nullable = false)
    private String nombrePuesto;

    @NotNull
    @Column(name = "descripcion", nullable = false, length = Integer.MAX_VALUE)
    private String descripcion;

    @NotNull
    @Column(name = "\"numeroPlazas\"", nullable = false)
    private Integer numeroPlazas;

    @NotNull
    @Column(name = "estatus", nullable = false)
    private Boolean estatus = false;

    @NotNull
    @Column(name = "\"fechaPublicacion\"", nullable = false)
    private LocalTime fechaPublicacion;

    @Size(max = 255)
    @Column(name = "requisitos")
    private String requisitos;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"Perfiles_idPerfil\"", nullable = false)
    private Perfil perfilesIdperfil;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"AreasDgp_idArea\"", nullable = false)
    private AreaDgp areasdgpIdarea;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"Horarios_idHorario\"", nullable = false)
    private Horario horariosIdhorario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"Modalidades_idModalidad\"", nullable = false)
    private Modalidad modalidadesIdmodalidad;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"Asignaciones_idAsignacion\"", nullable = false)
    private Asignacion asignacionesIdasignacion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"Carreras_idCarerra\"", nullable = false)
    private Carrera carrerasIdcarerra;

}