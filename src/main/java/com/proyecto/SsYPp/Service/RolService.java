package com.proyecto.SsYPp.Service;

import org.springframework.stereotype.Service;
import com.proyecto.SsYPp.Entity.Rol;

@Service
public interface RolService {
    public Rol getRol(Integer id);
}
