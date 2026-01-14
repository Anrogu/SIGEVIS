package com.proyecto.SsYPp.Service;

import com.proyecto.SsYPp.Dto.NoticiaDto;
import com.proyecto.SsYPp.Dto.PostulacionDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostulacionService {
    public PostulacionDto create(PostulacionDto postulacion);
    public List<PostulacionDto> getAll();
    public PostulacionDto get(Long id);
    public void delete (Long id);
    public PostulacionDto update(PostulacionDto postulacion);
}
