package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Dto.PostulacionAdminRowDto;
import com.proyecto.SsYPp.Repository.PostulacionRepository;
import org.springframework.web.bind.annotation.*;
import com.proyecto.SsYPp.Dto.PostulacionAdminDetalleDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/postulaciones/admin")
public class PostulacionAdminRestController {

    private final PostulacionRepository postulacionRepository;

    public PostulacionAdminRestController(PostulacionRepository postulacionRepository) {
        this.postulacionRepository = postulacionRepository;
    }

    // =========================
    // GET: todas las postulaciones (ADMIN)
    // =========================
    @GetMapping("/todas")
    public List<PostulacionAdminRowDto> todas() {

        List<PostulacionAdminRowDto> rows = postulacionRepository.findAllAdminRows();

        // rellenamos estatusTexto sin depender del campo del status (por ahora)
        return rows.stream()
                .map(r -> new PostulacionAdminRowDto(
                        r.id(),
                        r.fechaPostulacion(),
                        r.prestadorNombre(),
                        r.prestadorEmail(),
                        r.vacanteNombre(),
                        r.estatusId(),
                        estatusTexto(r.estatusId())
                ))
                .toList();
    }
    @GetMapping("/detalle/{id}")
    public ResponseEntity<PostulacionAdminDetalleDto> detalle(@PathVariable Long id) {
        PostulacionAdminDetalleDto dto = postulacionRepository.findAdminDetalleById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    private String estatusTexto(Long id) {
        if (id == null) return "Nuevo";
        return switch (id.intValue()) {
            case 3 -> "Aceptado";
            case 2 -> "Rechazado";
            default -> "Nuevo";
        };
    }
}
