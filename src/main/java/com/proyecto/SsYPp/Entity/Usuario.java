package com.proyecto.SsYPp.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.time.OffsetTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    @Size(max = 15)
    @Column(name = "telefono")
    private String telefono; 

    @Column(name = "emailverifiedat")
    private OffsetDateTime emailverifiedat;

    // 🔒 NO debe exponerse nunca
    @JsonIgnore
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    // 🔒 Tampoco debe exponerse
    @JsonIgnore
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

    // ✅ Área del coordinador
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "areasdgp_idarea")
    private AreaDgp area;
}
