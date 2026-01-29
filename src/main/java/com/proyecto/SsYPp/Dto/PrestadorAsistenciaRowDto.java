package com.proyecto.SsYPp.Dto;

public class PrestadorAsistenciaRowDto {
    private Long id;
    private String nombre;
    private String email;
    private String carrera;
    private AsistenciaHoyDto hoy;

    public PrestadorAsistenciaRowDto() {}

    public PrestadorAsistenciaRowDto(Long id, String nombre, String email, String carrera, AsistenciaHoyDto hoy) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.carrera = carrera;
        this.hoy = hoy;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }

    public AsistenciaHoyDto getHoy() { return hoy; }
    public void setHoy(AsistenciaHoyDto hoy) { this.hoy = hoy; }
}
