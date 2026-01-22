package com.proyecto.SsYPp.Controller.Coordinador;

import com.proyecto.SsYPp.Entity.Horario;
import com.proyecto.SsYPp.Entity.Modalidad;
import com.proyecto.SsYPp.Repository.HorarioRepository;
import com.proyecto.SsYPp.Repository.ModalidadRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller para catálogos del COORDINADOR
 */
@RestController
@RequestMapping("/coordinador")
public class CoordinadorCatalogosController {

    private final ModalidadRepository modalidadRepository;
    private final HorarioRepository horarioRepository;

    public CoordinadorCatalogosController(
            ModalidadRepository modalidadRepository,
            HorarioRepository horarioRepository
    ) {
        this.modalidadRepository = modalidadRepository;
        this.horarioRepository = horarioRepository;
    }

    @GetMapping("/modalidades/getAll")
    public List<Modalidad> getAllModalidades() {
        return modalidadRepository.findAll();
    }

    @GetMapping("/horarios/getAll")
    public List<Horario> getAllHorarios() {
        return horarioRepository.findAll();
    }
}
