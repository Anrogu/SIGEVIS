package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Dto.VacanteDto;
import com.proyecto.SsYPp.Dto.UsuarioDto;
import com.proyecto.SsYPp.Service.UsuarioService;
import com.proyecto.SsYPp.Service.VacanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/vacantes")
public class VacanteController {

    @Autowired
    private VacanteService vacanteService;

    @Autowired
    private UsuarioService usuarioService;

    // 1. VISTA: Carga el HTML y busca el ID del usuario logueado
    @GetMapping
    public String index(Model model, Authentication authentication) {
        model.addAttribute("vacante", new VacanteDto());

        // Lógica para obtener el ID del usuario actual
        Integer currentUserId = 1;

        if (authentication != null) {
            String email = authentication.getName(); // Spring Security guarda el email/username aquí
            System.out.println("Usuario logueado: " + email);

            try {
                UsuarioDto usuario = usuarioService.getUsuarioByEmail(email);

                if (usuario != null && usuario.getIdusuario() != null) {
                    currentUserId = usuario.getIdusuario();
                    System.out.println("ID encontrado: " + currentUserId);
                }
            } catch (Exception e) {
                System.err.println("Error buscando usuario por email: " + e.getMessage());
            }
        }

        // Pasamos el ID real al HTML para que el input hidden lo tome
        model.addAttribute("currentUserId", currentUserId);

        return "admin/vacantes";
    }

    // 2. API: Obtener todas las vacantes
    @GetMapping("/getAll")
    @ResponseBody
    public List<VacanteDto> getAll() {
        return vacanteService.getAll();
    }

    // 3. API: Crear nueva vacante
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody VacanteDto vacanteDto) {
        try {
            vacanteService.create(vacanteDto);
            return ResponseEntity.ok(vacanteDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear: " + e.getMessage());
        }
    }

    // 4. API: Actualizar vacante
    @PutMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody VacanteDto vacanteDto) {
        try {
            vacanteDto.setIdVacantes(id);
            vacanteService.update(vacanteDto);
            return ResponseEntity.ok("Actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar: " + e.getMessage());
        }
    }

    // 5. API: Eliminar vacante
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            vacanteService.delete(id);
            return ResponseEntity.ok("Eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar: " + e.getMessage());
        }
    }
}