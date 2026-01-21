package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Entity.Contador;
import com.proyecto.SsYPp.Repository.ContadorRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/contador")
public class ContadorController {
    ContadorRepository contadorRepository;

}
