package com.proyecto.SsYPp.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "Vacantes")
public class Vacante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVacantes;

    private String nombrePuesto;
    private String descripcion;
    private Integer numeroPlazas;
    private Boolean estatus;
    private LocalDateTime fechaPublicacion;
    private String requisitos;
    private String carrera;

    // Claves Foráneas (IDs)
    private Integer Perfiles_idPerfil;
    private Long AreasDgp_idArea;
    private Integer Horarios_idHorario;
    private Integer Modalidades_idModalidad;
    private Long Asignaciones_idAsignacion;

    // Constructor vacío (requerido por JPA)
    public Vacante() {
    }

    // --- Getters y Setters Manuales (Si NO usas Lombok) ---

    public Long getIdVacantes() {
        return idVacantes;
    }

    public void setIdVacantes(Long idVacantes) {
        this.idVacantes = idVacantes;
    }

    public String getNombrePuesto() {
        return nombrePuesto;
    }

    public void setNombrePuesto(String nombrePuesto) {
        this.nombrePuesto = nombrePuesto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getNumeroPlazas() {
        return numeroPlazas;
    }

    public void setNumeroPlazas(Integer numeroPlazas) {
        this.numeroPlazas = numeroPlazas;
    }

    public Boolean getEstatus() {
        return estatus;
    }

    public void setEstatus(Boolean estatus) {
        this.estatus = estatus;
    }

    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public Integer getPerfiles_idPerfil() {
        return Perfiles_idPerfil;
    }

    public void setPerfiles_idPerfil(Integer perfiles_idPerfil) {
        Perfiles_idPerfil = perfiles_idPerfil;
    }

    public Long getAreasDgp_idArea() {
        return AreasDgp_idArea;
    }

    public void setAreasDgp_idArea(Long areasDgp_idArea) {
        AreasDgp_idArea = areasDgp_idArea;
    }

    public Integer getHorarios_idHorario() {
        return Horarios_idHorario;
    }

    public void setHorarios_idHorario(Integer horarios_idHorario) {
        Horarios_idHorario = horarios_idHorario;
    }

    public Integer getModalidades_idModalidad() {
        return Modalidades_idModalidad;
    }

    public void setModalidades_idModalidad(Integer modalidades_idModalidad) {
        Modalidades_idModalidad = modalidades_idModalidad;
    }

    public Long getAsignaciones_idAsignacion() {
        return Asignaciones_idAsignacion;
    }

    public void setAsignaciones_idAsignacion(Long asignaciones_idAsignacion) {
        Asignaciones_idAsignacion = asignaciones_idAsignacion;
    }
}