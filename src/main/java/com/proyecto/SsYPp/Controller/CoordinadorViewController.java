package com.proyecto.SsYPp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/coordinador")
public class CoordinadorViewController {

    @GetMapping("/index")
    public String index() {
        return "coordinador/index";
    }

    @GetMapping("/noticias")
    public String noticias() {
        return "coordinador/noticias";
    }

    @GetMapping("/vacantes")
    public String vacantes() {
        return "coordinador/vacantes";
    }

    @GetMapping("/postulaciones")
    public String postulaciones() {
        return "coordinador/postulaciones";
    }

    @GetMapping("/asignaciones")
    public String asignaciones() {
        return "coordinador/asignaciones";
    }

    @GetMapping("/actividades")
    public String actividades() {
        return "coordinador/actividades";
    }

    @GetMapping("/asistencias")
    public String asistencias() {
        return "coordinador/asistencias";
    }
}
