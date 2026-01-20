package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Dto.UsuarioDto;
import com.proyecto.SsYPp.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // ✅ NECESARIO OTRA VEZ
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // 1. VISTA: Agregamos el modelo vacío para que Thymeleaf no falle
    @GetMapping
    public String index(Model model) {
        model.addAttribute("usuario", new UsuarioDto()); // ✅ ESTO ARREGLA EL ERROR
        return "admin/usuarios";
    }

    // --- API JSON (Igual que antes) ---
    @GetMapping("/getAll")
    @ResponseBody
    public List<UsuarioDto> getAll() {
        return usuarioService.getAll(PageRequest.of(0, 1000)).getContent();
    }

    @PostMapping("/guardar")
    @ResponseBody
    public ResponseEntity<?> guardar(@RequestBody UsuarioDto usuarioDto) {
        try {
            if (usuarioDto.getIdusuario() != null && usuarioDto.getIdusuario() > 0) {
                usuarioService.update(usuarioDto);
                return ResponseEntity.ok("Usuario actualizado correctamente.");
            } else {
                usuarioService.create(usuarioDto);
                return ResponseEntity.ok("Usuario creado correctamente.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            usuarioService.delete(id);
            return ResponseEntity.ok("Eliminado.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar.");
        }
    }

    @GetMapping("/cambiar-status/{id}")
    @ResponseBody
    public ResponseEntity<?> cambiarStatus(@PathVariable Integer id) {
        try {
            boolean nuevoStatus = usuarioService.toggleStatus(id);
            return ResponseEntity.ok(nuevoStatus ? "Usuario ACTIVADO." : "Usuario DESACTIVADO.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}