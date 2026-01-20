package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Dto.AsignacionDto;
import com.proyecto.SsYPp.Service.AsignacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller // 1. Ahora es un Controlador de Vistas
@RequestMapping("/admin/asignaciones") // 2. Ruta protegida bajo Admin
public class AsignacionesController {

    @Autowired
    private AsignacionService asignacionService;

    // --- LISTAR (Vista Principal) ---
    @GetMapping
    public String index(Model model) {
        List<AsignacionDto> listaAsignaciones = asignacionService.getAll();
        model.addAttribute("asignaciones", listaAsignaciones);
        model.addAttribute("asignacion", new AsignacionDto());
        return "admin/asignaciones";
    }

    // --- GUARDAR (Crear o Editar) ---
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute AsignacionDto asignacionDto, RedirectAttributes redirectAttributes) {
        try {
            if (asignacionDto.getId() != null) {
                asignacionService.update(asignacionDto);
                redirectAttributes.addFlashAttribute("mensajeExito", "Asignación actualizada correctamente.");
            } else {
                asignacionService.create(asignacionDto);
                redirectAttributes.addFlashAttribute("mensajeExito", "Asignación creada correctamente.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al guardar: " + e.getMessage());
        }

        // Redirige a la misma página para recargar la tabla
        return "redirect:/admin/asignaciones";
    }

    // --- ELIMINAR ---
    @GetMapping("/eliminar/{id}")
    public String deleteAsignacion(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            asignacionService.delete(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Se ha borrado la asignación con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "No se pudo eliminar la asignación.");
        }
        return "redirect:/admin/asignaciones";
    }
}