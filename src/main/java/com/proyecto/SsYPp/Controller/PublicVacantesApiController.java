package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Dto.VacanteDto;
import com.proyecto.SsYPp.Service.VacanteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/vacantes")
public class PublicVacantesApiController {

    private final VacanteService vacanteService;

    public PublicVacantesApiController(VacanteService vacanteService) {
        this.vacanteService = vacanteService;
    }

    // ✅ LISTA PÚBLICA (solo publicadas)
    @GetMapping("/getAll")
    public ResponseEntity<List<VacanteDto>> getAllPublicas() {
        List<VacanteDto> todas = vacanteService.getAll();

        // Solo publicadas (estatus = true)
        List<VacanteDto> publicadas = todas.stream()
                .filter(v -> v.getEstatus() != null && v.getEstatus())
                .toList();

        return ResponseEntity.ok(publicadas);
    }

    // ✅ DETALLE PÚBLICO
    @GetMapping("/{id}")
    public ResponseEntity<VacanteDto> detalle(@PathVariable Long id) {
        VacanteDto v = vacanteService.get(id);

        // Si no existe o no está publicada, regresamos 404
        if (v == null || v.getEstatus() == null || !v.getEstatus()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(v);
    }
}
