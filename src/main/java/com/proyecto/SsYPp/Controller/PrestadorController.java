package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrestadorController {

    private final UsuarioService usuarioService;

    public PrestadorController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/prestador/perfil")
    public String perfilPrestador(Authentication authentication, Model model) {
        String correo = authentication.getName(); // normalmente es el username (correo)
        var usuario = usuarioService.buscarPorCorreo(correo);

        model.addAttribute("usuario", usuario);
        return "prestador/perfil"; // => templates/prestador/perfil.html
    }
}
