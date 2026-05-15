package com.proyecto.SsYPp.Service.Impl;

import com.proyecto.SsYPp.Entity.Carrera;
import com.proyecto.SsYPp.Service.CarreraService;
import com.proyecto.SsYPp.Repository.CarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarreraImpl implements CarreraService {
    @Autowired
    CarreraRepository carreraRepository;

    @Override
    public List<Carrera> getAllCarreras() {
        List<Carrera> carrera = carreraRepository.findAll();
        return new ArrayList<>(carrera);
    }

    @Override
    public List<Carrera> obtenerCarrerasPorEscuela(Integer idEscuela) {

        return carreraRepository.findByEscuela_Idescuela(idEscuela);
    }

    @Override
    public Carrera guardarCarrera(Carrera carrera) {

        return carreraRepository.save(carrera);
    }

    @Override
    public void eliminarCarrera(Integer id) {

        carreraRepository.deleteById(id);
    }
}
