package com.proyecto.SsYPp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    /*@GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }*/

    @GetMapping("/index")
    public String showIndexPage() {
        return "index";
    }

    @GetMapping("/")
    public String redirectToIndex() {
        return "redirect:/index";
    }

// --- Módulos del Dashboard ---

    @GetMapping("/vacantes")
    public String mostrarVacantes() {
        return "vacantes";
    }

    @GetMapping("/asignaciones")
    public String mostrarAsignaciones() {
        return "asignaciones";
    }

    @GetMapping("/auditorias")
    public String mostrarAuditorias() {
        return "auditorias";
    }

    @GetMapping("/catalogos")
    public String mostrarCatalogos() {
        return "catalogos";
    }
}