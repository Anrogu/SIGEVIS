package com.proyecto.SsYPp.Service;

import com.proyecto.SsYPp.Entity.Noticia;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoticiaService {
    public Noticia create(Noticia noticia);
    public List<Noticia> getAll();
    public Noticia get(Long id);
    public void delete (Long id);
    public Noticia update(Noticia noticia);
}
