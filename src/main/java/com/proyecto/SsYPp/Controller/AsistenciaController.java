package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Entity.Asistencia;
import com.proyecto.SsYPp.Repository.AsistenciaRepository;
import com.proyecto.SsYPp.Service.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/asistencias")
public class AsistenciaController {

    @Autowired
    AsistenciaService service;
    @Autowired
    AsistenciaRepository asistenciaRepository;
    @Autowired
    private AsistenciaService asistenciaService;

    @PostMapping("/entrada/{UsuarioIdusuario}")
    public Asistencia entrada(@PathVariable Long UsuarioIdusuario) {
        return service.registrarEntrada(UsuarioIdusuario);
    }


    @PostMapping("/salida/{UsuarioIdusuario}")
    public Asistencia salida(@PathVariable Long UsuarioIdusuario) {
        return service.registrarSalida(UsuarioIdusuario);
    }

    @GetMapping("/total/{idUsuario}")
    public BigDecimal totalHoras(@PathVariable Long idUsuario) {
        return asistenciaService.totalHoras(idUsuario);
    }

}