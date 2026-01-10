package com.proyecto.SsYPp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PublicController {

    // Página principal pública (sin login)
    @GetMapping("/public/noticias")
    public String noticiasPublicas() {
        return "public/noticias-publicas";
        // busca: templates/public/noticias-publicas.html
    }

        @GetMapping("/login")
        public String login() {
            return "login"; // templates/login.html
        }

    @GetMapping("/registro")
    public String registro() {
        return "register";
        }
    }
