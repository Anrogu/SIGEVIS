package com.proyecto.SsYPp.Service;

import com.proyecto.SsYPp.Dto.VacanteDto;

import java.util.List;

public interface VacanteService {
    VacanteDto create(VacanteDto vacante);
    List<VacanteDto> getAll();
    VacanteDto get(Long id);
    void delete(Long id);
    VacanteDto update(VacanteDto vacante);

    // ✅ NUEVO: vacantes para prestador excluyendo las ya postuladas
    List<VacanteDto> getVacantesParaPrestadorSinPostuladas(Long idUsuario);
}
