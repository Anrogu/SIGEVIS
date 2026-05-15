package com.proyecto.SsYPp.Service;
import com.proyecto.SsYPp.Entity.Carrera;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarreraService {

    public List<Carrera> getAllCarreras();
    List<Carrera> obtenerCarrerasPorEscuela(Integer idEscuela);
    Carrera guardarCarrera(Carrera carrera);

    void eliminarCarrera(Integer id);
}
