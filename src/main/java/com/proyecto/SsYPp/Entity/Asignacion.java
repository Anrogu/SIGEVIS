package com.proyecto.SsYPp.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetTime;

@Getter
@Setter
@Entity
@Table(name = "\"Asignaciones\"")
public class Asignacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idAsignacion\"", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "\"fechaInicio\"", nullable = false)
    private OffsetTime fechaInicio;

    @NotNull
    @Column(name = "\"fechaFin\"", nullable = false)
    private OffsetTime fechaFin;

    @NotNull
    @Column(name = "estatus", nullable = false)
    private Boolean estatus = false;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"Usuarios_idUsuario\"", nullable = false)
    private Usuario usuariosIdusuario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"Postulaciones_idPostulacion\"", nullable = false)
    private Postulacion postulacionesIdpostulacion;

}