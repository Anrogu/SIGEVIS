package com.proyecto.SsYPp.Service.Impl;

import com.proyecto.SsYPp.Entity.Rol;
import com.proyecto.SsYPp.Repository.RolRepository;
import com.proyecto.SsYPp.Service.RolService;

public class RolImpl implements RolService {
    private RolRepository rolRepository;

    @Override
    //INCORRECTO
    public Rol getRol(Integer id) {
        Rol rol = rolRepository.getOne(id);
        return rol;
    }
}
