package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Dto.UsuarioDto;
import com.proyecto.SsYPp.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String index(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<UsuarioDto> usuariosPage = usuarioService.getAll(PageRequest.of(page, 9));
        model.addAttribute("usuarios", usuariosPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usuariosPage.getTotalPages());
        model.addAttribute("usuario", new UsuarioDto());

        return "usuarios";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute UsuarioDto usuarioDto, RedirectAttributes redirectAttributes) {
        try {
            if (usuarioDto.getIdusuario() != null && usuarioDto.getIdusuario() > 0) {
                usuarioService.update(usuarioDto);
                redirectAttributes.addFlashAttribute("mensajeExito", "Usuario actualizado correctamente.");
            } else {
                usuarioService.create(usuarioDto);
                redirectAttributes.addFlashAttribute("mensajeExito", "Usuario creado correctamente.");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Ocurrió un error inesperado al guardar.");
        }

        return "redirect:/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        usuarioService.delete(id);
        return "redirect:/usuarios";
    }

    @GetMapping("/cambiar-status/{id}")
    public String cambiarStatus(@PathVariable Integer id) {
        return "redirect:/usuarios";
    }
}