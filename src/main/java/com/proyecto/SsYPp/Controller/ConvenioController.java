package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Model.Convenio;
import com.proyecto.SsYPp.Repository.EscuelaRepository;
import com.proyecto.SsYPp.Service.ConvenioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/convenios")
public class ConvenioController {

    private final ConvenioService service;
    private final EscuelaRepository escuelaRepository;

    public ConvenioController(ConvenioService service, EscuelaRepository escuelaRepository) {
        this.service = service;
        this.escuelaRepository = escuelaRepository;
    }

    // 🔥 AL ENTRAR A /convenios TE MANDA A LA LISTA
    @GetMapping({"", "/", "/lista"})
    public String listar(Model model) {
        model.addAttribute("convenios", service.findAll());
        return "convenios/lista";  // templates/convenios/lista.html
    }

    // 🔥 EL SUBMÓDULO DEL DASHBOARD DEBE REDIRIGIR AQUI
    @GetMapping("/redirect")
    public String redirectToLista() {
        return "redirect:/convenios/lista";
    }

    // FORMULARIO NUEVO
    @GetMapping("/crear")
    public String crear(Model model) {
        model.addAttribute("convenio", new Convenio());
        model.addAttribute("escuelas", escuelaRepository.findAll());
        return "convenios/form";
    }

    // GUARDAR
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Convenio convenio) {
        service.save(convenio);
        return "redirect:/convenios/lista";
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("convenio", service.findById(id));
        model.addAttribute("escuelas", escuelaRepository.findAll());
        return "convenios/form";
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/convenios/lista";
    }
}
