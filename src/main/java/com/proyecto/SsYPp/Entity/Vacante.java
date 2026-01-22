package com.proyecto.SsYPp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "\"Vacantes\"")
public class Vacante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idVacantes\"", nullable = false)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "\"nombrePuesto\"", nullable = false)
    private String nombrePuesto;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull
    @Column(name = "\"numeroPlazas\"", nullable = false)
    private Integer numeroPlazas;

    @NotNull
    @Column(name = "estatus", nullable = false)
    private Boolean estatus;

    @NotNull
    @Column(name = "\"fechaPublicacion\"", nullable = false)
    private LocalTime fechaPublicacion;

    @Column(name = "requisitos")
    private String requisitos;

    @ManyToOne(optional = false)
    @JoinColumn(name = "\"AreasDgp_idArea\"")
    private AreaDgp areasdgpIdarea;

    @ManyToOne(optional = false)
    @JoinColumn(name = "\"Horarios_idHorario\"")
    private Horario horariosIdhorario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "\"Modalidades_idModalidad\"")
    private Modalidad modalidadesIdmodalidad;

    @ManyToOne(optional = false)
    @JoinColumn(name = "\"Carreras_idCarerra\"")
    private Carrera carrerasIdcarerra;

    @ManyToOne(optional = false)
    @JoinColumn(name = "\"Usuarios_idUsuario\"")
    private Usuario creadoPor;
}
