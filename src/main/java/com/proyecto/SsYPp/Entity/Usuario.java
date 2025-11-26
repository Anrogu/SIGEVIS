package com.proyecto.SsYPp.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.time.OffsetTime;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Size(max = 255)
    @NotNull
    @Column(name = "primerapellido", nullable = false)
    private String primerapellido;

    @Size(max = 255)
    @NotNull
    @Column(name = "segundoapellido", nullable = false)
    private String segundoapellido;

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "emailverifiedat")
    private OffsetDateTime emailverifiedat;

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Size(max = 255)
    @Column(name = "rememberedtoken")
    private String rememberedtoken;

    @Column(name = "createdat")
    private OffsetTime createdat;

    @Column(name = "updatedat")
    private OffsetTime updatedat;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idrol")
    private Rol idrol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carreras_idcarrera")
    private Carrera carrerasIdcarrera;

}