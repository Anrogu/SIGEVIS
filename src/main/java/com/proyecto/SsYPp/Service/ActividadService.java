package com.proyecto.SsYPp.Service;

import com.proyecto.SsYPp.Dto.ActividadDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ActividadService {
    ActividadDto create(ActividadDto actividadDto);
    List<ActividadDto> getAll();
    ActividadDto get(Long id);
    void delete(Long id);
    ActividadDto update(ActividadDto actividadDto);
}
