package com.proyecto.SsYPp.Model;

import jakarta.persistence.*;
import lombok.Data;

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
}
