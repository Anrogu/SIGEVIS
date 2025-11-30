package com.proyecto.SsYPp.Service.Impl;

import com.proyecto.SsYPp.Entity.Rol;
import com.proyecto.SsYPp.Repository.RolRepository;
import com.proyecto.SsYPp.Service.RolService;
import org.springframework.stereotype.Service;

@Service
public class RolImpl implements RolService {
    private RolRepository rolRepository;

    public RolImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    //INCORRECTO
    public Rol getRol (Integer id) {
        Rol rol= rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        return(rol);
    }
}
