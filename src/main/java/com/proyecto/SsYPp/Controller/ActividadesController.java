package com.proyecto.SsYPp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/actividades")
public class ActividadesController {

    @GetMapping
    public String index() {
        return "admin/actividades";
    }
}