package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Entity.Modalidad;
import com.proyecto.SsYPp.Repository.ModalidadRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/modalidades")
public class ModalidadController {

    private final ModalidadRepository modalidadRepository;

    public ModalidadController(ModalidadRepository modalidadRepository) {
        this.modalidadRepository = modalidadRepository;
    }

    // GET: /admin/modalidades/getAll
    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Modalidad> getAll() {
        return modalidadRepository.findAll();
    }
}
