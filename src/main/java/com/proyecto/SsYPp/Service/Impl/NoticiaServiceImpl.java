package com.proyecto.SsYPp.Service.Impl;

import com.proyecto.SsYPp.Entity.Noticia;
import com.proyecto.SsYPp.Entity.Usuario;
import com.proyecto.SsYPp.Repository.NoticiaRepository;
import com.proyecto.SsYPp.Service.NoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticiaServiceImpl implements NoticiaService {
    @Autowired
    NoticiaRepository noticiaRepository;
    @Override
    public Noticia create(Noticia noticia) {
         noticia = noticiaRepository.save(noticia);
         return noticia;
    }

    @Override
    public List<Noticia> getAll() {
        List<Noticia> noticia = noticiaRepository.findAll();
        return noticia.stream()
                .collect(Collectors.toList());
    }

    @Override
    public Noticia get(Long id) {
        Noticia noticia = noticiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return noticia;
    }

    @Override
    public void delete(Long id) { noticiaRepository.deleteById(id);}

    @Override
    public Noticia update(Noticia noticia) {
        noticia=noticiaRepository.save(noticia);
        return noticia;
    }

}
