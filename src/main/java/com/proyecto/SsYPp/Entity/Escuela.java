package com.proyecto.SsYPp.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "\"Escuela\"")
@Data
public class Escuela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEscuela")
    private Integer idEscuela;

    private String nombre;
    private String claveCentroTrabajo;
    private String coordinadorUnidad;
    private String formatoCorreo;

    @OneToMany(mappedBy = "escuela")
    private Set<Convenio> convenios = new LinkedHashSet<>();

}
