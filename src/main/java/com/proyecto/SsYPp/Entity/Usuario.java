package com.proyecto.SsYPp.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;


@Entity
@Data
@Table(name = "users")
public class Usuario  implements Serializable{

    @Id
    private Long id_usuario;

    private String name;

    private String email;

    private String email_verified_at;

    private String password;

    private String remember_token;

    private Timestamp created_at;

    private Timestamp updated_at;

    private Boolean status;

    //colocar MANYTOONE requiere crear la variable apartir del objeto
    private Integer rol_id;

    private String matricula;

    //colocar MANYTOONE requiere crear la variable apartir del objeto
    private Integer escuela_id;

    //colocar MANYTOONE requiere crear la variable apartir del objeto
    private Integer carrera_id;

    //EJEMPLO
    //@ManyToOne
    //@JoinColumn(name = "uaads")
    //private Catuads uaads;
}