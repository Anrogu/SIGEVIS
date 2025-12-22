package com.proyecto.SsYPp.Service;

import com.proyecto.SsYPp.Dto.VacanteDto;
import com.proyecto.SsYPp.Entity.Vacante;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VacanteService {
    public VacanteDto create(VacanteDto vacante);
    public List<VacanteDto> getAll();
    public VacanteDto get(Long id);
    public void delete (Long id);
    public VacanteDto update(VacanteDto vacante);
}
