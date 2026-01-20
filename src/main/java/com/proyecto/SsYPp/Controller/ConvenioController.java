package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Entity.Convenio;
import com.proyecto.SsYPp.Repository.EscuelaRepository;
import com.proyecto.SsYPp.Service.ConvenioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/admin/convenios")
public class ConvenioController {

    private final ConvenioService service;
    private final EscuelaRepository escuelaRepository;

    public ConvenioController(ConvenioService service, EscuelaRepository escuelaRepository) {
        this.service = service;
        this.escuelaRepository = escuelaRepository;
    }

    // 1. VISTA CON MODELO (Corregido)
    @GetMapping
    public String index(Model model) {
        // 1. Objeto vacío para el formulario (evita error de th:object)
        model.addAttribute("convenio", new Convenio());

        // 2. Página vacía para la tabla (evita error de "content cannot be found on null")
        // Esto engaña a Thymeleaf para que renderice la tabla vacía sin error.
        model.addAttribute("convenios", Page.empty());

        return "admin/convenios";
    }

    // --- API JSON ---
    @GetMapping("/getAll")
    @ResponseBody
    public List<Convenio> getAll() {
        return service.findAll(PageRequest.of(0, 1000)).getContent();
    }

    @GetMapping("/getEscuelas")
    @ResponseBody
    public ResponseEntity<?> getEscuelas() {
        return ResponseEntity.ok(escuelaRepository.findAll());
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody Convenio convenio) {
        try {
            service.save(convenio);
            return ResponseEntity.ok("Guardado.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            service.delete(id);
            return ResponseEntity.ok("Eliminado.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar.");
        }
    }
}