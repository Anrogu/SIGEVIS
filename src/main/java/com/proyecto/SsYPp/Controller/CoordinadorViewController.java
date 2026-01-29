package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Dto.AsignacionDto;
import com.proyecto.SsYPp.Service.AsignacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/coordinador")
public class CoordinadorViewController {

    @Autowired
    private AsignacionService asignacionService; // ✅ NUEVO

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

    @GetMapping("/actividades")
    public String actividades() {
        return "coordinador/actividades";
    }

    @GetMapping("/asistencias")
    public String asistencias() {
        return "coordinador/asistencias";
    }
}
