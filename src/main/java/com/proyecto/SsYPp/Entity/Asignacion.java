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

    @Column(name = "\"fechaFin\"")
    private OffsetTime fechaFin;

    @Column(name = "activo")
    private Boolean activo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"postulaciones_idpostulacion\"", nullable = false)
    private Postulacion postulacionesIdpostulacion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"vacantes_idvacante\"", nullable = false)
    private Vacante vacantesIdvacante;

    public Usuario getUsuariosIdusuario() {
        if (this.postulacionesIdpostulacion != null) {
            return this.postulacionesIdpostulacion.getUsuariosIdusuario();
        }
        return null;
    }
}