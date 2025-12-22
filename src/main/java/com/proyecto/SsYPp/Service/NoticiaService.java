package com.proyecto.SsYPp.Service;

import com.proyecto.SsYPp.Dto.NoticiaDto;
import com.proyecto.SsYPp.Entity.Noticia;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoticiaService {
    public NoticiaDto create(NoticiaDto noticia);
    public List<NoticiaDto> getAll();
    public NoticiaDto get(Long id);
    public void delete (Long id);
    public NoticiaDto update(NoticiaDto noticia);
}
