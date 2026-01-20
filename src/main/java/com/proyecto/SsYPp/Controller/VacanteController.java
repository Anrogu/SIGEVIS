package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Dto.VacanteDto;
import com.proyecto.SsYPp.Service.VacanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; // ✅ Importante para respuestas HTTP
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/vacantes")
public class VacanteController {

    @Autowired
    private VacanteService vacanteService;

    // 1. VISTA: Este método SOLO carga el HTML (la estructura)
    @GetMapping
    public String index(Model model) {
        // Ya no necesitamos cargar la lista aquí, porque tu JS lo hará después
        // Retorna la plantilla que está en templates/admin/vacantes.html
        return "admin/vacantes";
    }

    // 2. API: Obtener todas las vacantes (Para tu JS: /getAll)
    @GetMapping("/getAll")
    @ResponseBody // ✅ Esto convierte la lista Java a JSON automáticamente
    public List<VacanteDto> getAll() {
        return vacanteService.getAll();
    }

    // 3. API: Crear nueva vacante (Para tu JS: /create)
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody VacanteDto vacanteDto) { // ✅ @RequestBody lee el JSON que envía tu JS
        try {
            vacanteService.create(vacanteDto);
            return ResponseEntity.ok(vacanteDto); // Devuelve 200 OK
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear: " + e.getMessage());
        }
    }

    // 4. API: Actualizar vacante (Para tu JS: /update/{id})
    @PutMapping("/update/{id}") // ✅ Cambiamos a PUT como lo pide tu JS
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody VacanteDto vacanteDto) {
        try {
            // Aseguramos que el ID del DTO coincida con el path
            vacanteDto.setIdVacantes(id);
            vacanteService.update(vacanteDto);
            return ResponseEntity.ok("Actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar: " + e.getMessage());
        }
    }

    // 5. API: Eliminar vacante (Para tu JS: /delete/{id})
    @DeleteMapping("/delete/{id}") // ✅ Cambiamos a DELETE como lo pide tu JS
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