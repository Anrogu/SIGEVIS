package com.proyecto.SsYPp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class PrestadorPendienteController {

    @GetMapping("/pendiente")
    public String pendiente() {
        return "prestador/pendiente";
    }
}
