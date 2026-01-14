package com.proyecto.SsYPp.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"StatusPostulacion\"")
public class StatusPostulacion {
    @Id
    @Column(name = "\"idPostulacion\"", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "status")
    private String status;

}