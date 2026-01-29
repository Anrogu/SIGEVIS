package com.proyecto.SsYPp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/coordinador")
public class CoordinadorAsistenciaViewController {

    @GetMapping("/asistencia")
    public String asistencia() {
        return "coordinador/asistencia"; // 👈 este debe existir como HTML
    }
}
