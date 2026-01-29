package com.proyecto.SsYPp.Service;

import com.proyecto.SsYPp.Dto.AsistenciaHoyDto;
import com.proyecto.SsYPp.Dto.PrestadorAsistenciaRowDto; // ✅ Importar
import com.proyecto.SsYPp.Entity.Asistencia;

import java.math.BigDecimal;
import java.util.List;

public interface AsistenciaService {

    Asistencia registrarEntrada(Long usuarioId);
    Asistencia registrarSalida(Long usuarioId);
    BigDecimal totalHoras(Long usuarioId);
    AsistenciaHoyDto obtenerEstatusHoy(Long usuarioId);

    List<PrestadorAsistenciaRowDto> obtenerListaParaCoordinador(String authName);
}