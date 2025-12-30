package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Entity.Convenio;
import com.proyecto.SsYPp.Repository.EscuelaRepository;
import com.proyecto.SsYPp.Service.ConvenioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @GetMapping
    public String index(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Convenio> conveniosPage = service.findAll(PageRequest.of(page, 9));

        model.addAttribute("convenios", conveniosPage);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", conveniosPage.getTotalPages());

        // Catálogos y formulario
        model.addAttribute("escuelas", escuelaRepository.findAll());
        model.addAttribute("convenio", new Convenio());

        return "convenios";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Convenio convenio) {
        service.save(convenio);
        return "redirect:/convenios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/convenios";
    }
}