package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Entity.Carrera;
import com.proyecto.SsYPp.Service.CarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Carreras")
public class CarreraController {

    @Autowired
    private CarreraService carreraService;

    @GetMapping("/getAll")
    public List<Carrera> getAll() {
        return carreraService.getAllCarreras();
    }
    @GetMapping("/escuela/{idEscuela}")
    public List<Carrera> obtenerCarrerasPorEscuela(
            @PathVariable Integer idEscuela) {

        return carreraService.obtenerCarrerasPorEscuela(idEscuela);
    }
}
