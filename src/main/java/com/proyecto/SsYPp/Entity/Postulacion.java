package com.proyecto.SsYPp.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "\"Postulaciones\"")
public class Postulacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idPostulacion\"", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "\"fechaPostulacion\"", nullable = false)
    private OffsetTime fechaPostulacion;

    @NotNull
    @Column(name = "estatus", nullable = false, length = Integer.MAX_VALUE)
    private String estatus;

    @Column(name = "comentarios", length = Integer.MAX_VALUE)
    private String comentarios;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"Usuarios_idUsuario\"", nullable = false)
    private Usuario usuariosIdusuario;

    @OneToMany(mappedBy = "postulacionesIdpostulacion")
    private Set<Asignacion> asignaciones = new LinkedHashSet<>();

}