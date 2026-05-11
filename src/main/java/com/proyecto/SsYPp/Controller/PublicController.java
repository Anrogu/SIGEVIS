package com.proyecto.SsYPp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PublicController {

    @GetMapping("/")
    public String home() {
        return "redirect:/public/noticias";
    }

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
    @GetMapping("/public/preguntas")
    public String preguntas() {
        return "public/preguntas";
    }

    // ✅ Contacto (público)
    @GetMapping("/public/contacto")
    public String contacto() {
        return "public/contacto";
    }

    // Login
    @GetMapping("/login")
    public String login() {
        return "LogInAndRegister/login";
    }

}