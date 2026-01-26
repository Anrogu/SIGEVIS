package com.proyecto.SsYPp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPostulacionController {

    @GetMapping("/admin/postulaciones")
    public String vistaPostulacionesAdmin() {
        return "admin/postulaciones";
    }
}
