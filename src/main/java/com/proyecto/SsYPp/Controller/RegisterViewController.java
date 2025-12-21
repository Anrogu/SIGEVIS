package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Service.CarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterViewController {
    @Autowired
    private CarreraService carreraService;

    @GetMapping("/register") // La ruta limpia para ver el formulario
    public String showRegistrationForm(Model model) {
        model.addAttribute("carreras", carreraService.getAllCarreras());

        // 2. Retornar la plantilla
        return "register"; // Retorna el archivo crearcuenta.html
    }
}