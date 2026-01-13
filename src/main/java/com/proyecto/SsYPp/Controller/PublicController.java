package com.proyecto.SsYPp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicController {

    // Noticias públicas
    @GetMapping("/public/noticias")
    public String noticiasPublicas() {
        return "public/noticias-publicas";
    }
    @GetMapping("/public/perfil")
    public String perfilPublico() {
        return "public/noticias-publicas";
    }
    @GetMapping("/public/vacantes")
    public String vacantesPublicas() {
        return "public/vacantes-publicas";
    }

    // Login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Registro
    @GetMapping("/registro")
    public String registro() {
        return "register";
    }
}
