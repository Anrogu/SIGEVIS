package com.proyecto.SsYPp.Dto;

public class AsistenciaHoyDto {
    private boolean existe;
    private boolean tieneEntrada;
    private boolean tieneSalida;
    private String horaEntrada; // "08:10"
    private String horaSalida;  // "14:05"
    private String estado;      // "SIN_REGISTRO" | "PENDIENTE_SALIDA" | "COMPLETA"

    public AsistenciaHoyDto() {}

    // getters & setters
    public boolean isExiste() { return existe; }
    public void setExiste(boolean existe) { this.existe = existe; }
    public boolean isTieneEntrada() { return tieneEntrada; }
    public void setTieneEntrada(boolean tieneEntrada) { this.tieneEntrada = tieneEntrada; }
    public boolean isTieneSalida() { return tieneSalida; }
    public void setTieneSalida(boolean tieneSalida) { this.tieneSalida = tieneSalida; }
    public String getHoraEntrada() { return horaEntrada; }
    public void setHoraEntrada(String horaEntrada) { this.horaEntrada = horaEntrada; }
    public String getHoraSalida() { return horaSalida; }
    public void setHoraSalida(String horaSalida) { this.horaSalida = horaSalida; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
