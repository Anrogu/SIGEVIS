package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Dto.NoticiaDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("noticia", new NoticiaDto());
        return "admin/index";
    }
}