package com.proyecto.SsYPp.Service.Impl;

import com.proyecto.SsYPp.Entity.Escuelas;
import com.proyecto.SsYPp.Repository.EscuelasRepository;
import com.proyecto.SsYPp.Service.EscuelasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.proyecto.SsYPp.Repository.CarreraRepository;

import java.util.List;

@Service
public class EscuelasImpl implements EscuelasService {

    @Autowired
    private EscuelasRepository escuelasRepository;

    @Autowired
    private CarreraRepository carreraRepository;

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

        boolean tieneCarreras =
                carreraRepository.existsByEscuela_Idescuela(id);

        if (tieneCarreras) {

            throw new RuntimeException(
                    "No se puede eliminar la escuela porque tiene carreras registradas."
            );
        }

        escuelasRepository.deleteById(id);
    }
}