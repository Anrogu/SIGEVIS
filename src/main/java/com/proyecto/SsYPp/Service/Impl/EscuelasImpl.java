package com.proyecto.SsYPp.Service.Impl;

import com.proyecto.SsYPp.Entity.Escuelas;
import com.proyecto.SsYPp.Repository.EscuelasRepository;
import com.proyecto.SsYPp.Service.EscuelasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EscuelasImpl implements EscuelasService {

    @Autowired
    private EscuelasRepository escuelasRepository;

    @Override
    public List<Escuelas> obtenerEscuelas() {

        return escuelasRepository.findAll();
    }

    @Override
    public Escuelas guardarEscuela(Escuelas escuela) {

        return escuelasRepository.save(escuela);
    }
    @Override
    public void eliminarEscuela(Integer id) {

        escuelasRepository.deleteById(id);
    }
}