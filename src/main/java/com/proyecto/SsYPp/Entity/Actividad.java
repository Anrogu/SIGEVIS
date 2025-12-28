package com.proyecto.SsYPp.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "actividades")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idActividad\"", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private Long descripcion;

    @NotNull
    @Column(name = "\"horasDestinadas\"", nullable = false, precision = 4, scale = 1)
    private BigDecimal horasDestinadas;

    @NotNull
    @Column(name = "\"fechaActividad\"", nullable = false)
    private LocalDate fechaActividad;

    @Size(max = 255)
    @NotNull
    @Column(name = "\"estatusActividad\"", nullable = false)
    private String estatusActividad;

    @Size(max = 255)
    @Column(name = "\"tipoActividad\"")
    private String tipoActividad;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"IdUsuario\"", nullable = false)
    private Usuario idUsuario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"IdAsignacion\"", nullable = false)
    private Asignacion idAsignacion;

}