package com.proyecto.SsYPp.Service;

import com.proyecto.SsYPp.Entity.Escuelas;

import java.util.List;

public interface EscuelasService {

    List<Escuelas> obtenerEscuelas();

    Escuelas guardarEscuela(Escuelas escuela);
}