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
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"Postulaciones_idPostulacion\"", nullable = false)
    private Postulacion postulacionesIdpostulacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"Vacantes_idVacante\"")
    private Vacante vacantesIdvacante;

    @OneToMany(mappedBy = "idAsignacion")
    private Set<Actividad> actividades = new LinkedHashSet<>();

}