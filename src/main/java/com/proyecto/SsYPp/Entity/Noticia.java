package com.proyecto.SsYPp.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetTime;

@Getter
@Setter
@Entity
@Table(name = "noticias")
public class Noticia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idNoticias\"", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @NotNull
    @Column(name = "contenido", nullable = false, length = Integer.MAX_VALUE)
    private String contenido;

    @Size(max = 50)
    @NotNull
    @Column(name = "estatus", nullable = false, length = 50)
    private String estatus;

    @NotNull
    @Column(name = "\"fechaPublicacion\"", nullable = false)
    private OffsetTime fechaPublicacion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"AutorId_idUsuario\"", nullable = false)
    private Usuario autoridIdusuario;

}