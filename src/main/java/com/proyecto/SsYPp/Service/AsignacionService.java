package com.proyecto.SsYPp.Service;

import com.proyecto.SsYPp.Dto.AsignacionDto;
import com.proyecto.SsYPp.Dto.NoticiaDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AsignacionService {
    public AsignacionDto create(AsignacionDto asignacion);
    public List<AsignacionDto> getAll();
    public AsignacionDto get(Long id);
    public void delete (Long id);
    public AsignacionDto update(AsignacionDto asignacion);
}
