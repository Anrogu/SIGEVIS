package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Service.CarreraService;
import com.proyecto.SsYPp.Repository.EscuelasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterViewController {
    @Autowired
    private CarreraService carreraService;

    @Autowired
    private EscuelasRepository escuelasRepository;

    @GetMapping("/register") // La ruta limpia para ver el formulario
    public String showRegistrationForm(Model model) {
        model.addAttribute("carreras", carreraService.getAllCarreras());

        model.addAttribute("escuelas", escuelasRepository.findAll());

        return "logInAndRegister/register"; // Retorna el archivo crearcuenta.html
    }
}