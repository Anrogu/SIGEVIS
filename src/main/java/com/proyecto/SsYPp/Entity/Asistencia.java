package com.proyecto.SsYPp.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "\"asistencia\"")
public class Asistencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"idAsistencia\"", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"Usuario_IdUsuario\"", nullable = false)
    private Usuario usuario;

    @Column(name = "\"horaEntrada\"")
    private OffsetDateTime horaEntrada;

    @Column(name = "\"horaSalida\"")
    private OffsetDateTime horaSalida;

}