package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Entity.Horario;
import com.proyecto.SsYPp.Repository.HorarioRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/horarios")
public class HorarioController {

    private final HorarioRepository horarioRepository;

    public HorarioController(HorarioRepository horarioRepository) {
        this.horarioRepository = horarioRepository;
    }

    // GET: /admin/horarios/getAll
    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Horario> getAll() {
        return horarioRepository.findAll();
    }
}
