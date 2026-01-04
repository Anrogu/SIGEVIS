package com.proyecto.SsYPp.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "conveniosIdconvenio")
    private Set<ConvenioCarrera> convenioCarreras = new LinkedHashSet<>();
}
