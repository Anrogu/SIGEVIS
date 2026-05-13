package com.proyecto.SsYPp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

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

    @Column(name = "plazas_ocupadas")
    private Integer plazasOcupadas = 0;

    @NotNull
    @Column(name = "estatus", nullable = false)
    private Boolean estatus;

    // FECHA DE VENCIMIENTO
    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @NotNull
    @Column(name = "\"fechaPublicacion\"", nullable = false)
    private LocalTime fechaPublicacion;

    @Column(name = "requisitos")
    private String requisitos;

    @ManyToOne
    @JoinColumn(name = "\"AreasDgp_idArea\"")
    private AreaDgp areasdgpIdarea;

    @ManyToOne
    @JoinColumn(name = "\"Horarios_idHorario\"")
    private Horario horariosIdhorario;

    @ManyToOne
    @JoinColumn(name = "\"Modalidades_idModalidad\"")
    private Modalidad modalidadesIdmodalidad;

    // NOMBRE JAVA CORRECTO (sin typo)
    // NOMBRE DE COLUMNA BD SE QUEDA TAL CUAL (con typo: idCarerra)
    @ManyToOne
    @JoinColumn(name = "\"Carreras_idCarerra\"")
    private Carrera carrerasIdcarrera;

    @ManyToOne
    @JoinColumn(name = "\"Usuarios_idUsuario\"")
    private Usuario creadoPor;
}
