package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Entity.Escuelas;
import com.proyecto.SsYPp.Service.EscuelasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/escuelas")
public class AdminEscuelasController {

    @Autowired
    private EscuelasService escuelasService;

    @GetMapping
    public String vistaEscuelas(Model model) {

        model.addAttribute(
                "escuelas",
                escuelasService.obtenerEscuelas()
        );

        model.addAttribute(
                "nuevaEscuela",
                new Escuelas()
        );

        return "admin/escuelas";
    }

    @PostMapping("/guardar")
    public String guardarEscuela(
            @ModelAttribute Escuelas escuela) {

        escuelasService.guardarEscuela(escuela);

        return "redirect:/admin/escuelas";
    }
}