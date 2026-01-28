package com.proyecto.SsYPp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class PrestadorAsistenciaViewController {

    @GetMapping("/asistencia")
    public String asistencia() {
        return "prestador/asistencia";
    }
}
