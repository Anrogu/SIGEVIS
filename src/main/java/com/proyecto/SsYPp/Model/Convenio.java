package com.proyecto.SsYPp.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "\"Convenios\"")
@Data
public class Convenio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idConvenio")
    private Integer idConvenio;

    private String folio;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estatus;
    private String tipoConvenio;

    @ManyToOne
    @JoinColumn(name = "Escuela_idEscuela")
    private Escuela escuela;
}
