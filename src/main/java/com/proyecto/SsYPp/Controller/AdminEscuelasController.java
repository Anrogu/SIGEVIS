package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Entity.Carrera;
import com.proyecto.SsYPp.Entity.Escuelas;
import com.proyecto.SsYPp.Service.CarreraService;
import com.proyecto.SsYPp.Service.EscuelasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/escuelas")
public class AdminEscuelasController {

    @Autowired
    private EscuelasService escuelasService;

    @Autowired
    private CarreraService carreraService;

    @GetMapping
    public String vistaEscuelas(Model model) {

        // TABLA ESCUELAS
        model.addAttribute(
                "escuelas",
                escuelasService.obtenerEscuelas()
        );

        // TABLA CARRERAS
        model.addAttribute(
                "carreras",
                carreraService.getAllCarreras()
        );

        // FORM NUEVA ESCUELA
        model.addAttribute(
                "nuevaEscuela",
                new Escuelas()
        );

        // FORM NUEVA CARRERA
        model.addAttribute(
                "nuevaCarrera",
                new Carrera()
        );

        return "admin/escuelas";
    }

    @PostMapping("/guardar")
    public String guardarEscuela(
            @ModelAttribute Escuelas escuela) {

        escuelasService.guardarEscuela(escuela);

        return "redirect:/admin/escuelas";
    }

    @PostMapping("/guardar-carrera")
    public String guardarCarrera(
            @ModelAttribute Carrera carrera) {

        carreraService.guardarCarrera(carrera);

        return "redirect:/admin/escuelas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEscuela(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes) {

        try {

            escuelasService.eliminarEscuela(id);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Escuela eliminada correctamente."
            );

        } catch (RuntimeException e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    e.getMessage()
            );
        }

        return "redirect:/admin/escuelas";
    }

    @GetMapping("/eliminar-carrera/{id}")
    public String eliminarCarrera(@PathVariable Integer id) {

        carreraService.eliminarCarrera(id);

        return "redirect:/admin/escuelas";
    }
}