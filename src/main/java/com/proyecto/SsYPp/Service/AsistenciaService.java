package com.proyecto.SsYPp.Service;

import com.proyecto.SsYPp.Entity.Asistencia;

import java.math.BigDecimal;

public interface AsistenciaService {

    Asistencia registrarEntrada(Long usuarioId);

    Asistencia registrarSalida(Long usuarioId);

    BigDecimal totalHoras(Long usuarioId);
}
