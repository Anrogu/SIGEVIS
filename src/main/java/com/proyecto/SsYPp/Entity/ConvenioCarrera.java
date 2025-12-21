package com.proyecto.SsYPp.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ConvenioCarrera {
    @Id
    @Column(name = "\"idConvenio_Carrera\"", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Convenios_idConvenio")
    private Convenio conveniosIdconvenio;
}