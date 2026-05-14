package com.proyecto.SsYPp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "escuelas")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Escuelas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idescuela")
    private Integer idescuela;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @JsonIgnore
    @OneToMany(mappedBy = "escuela")
    private List<Carrera> carreras;
}