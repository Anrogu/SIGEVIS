package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Entity.*;
import com.proyecto.SsYPp.Repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/coordinador/vacantes")
public class CoordinadorVacanteController {

    private final UsuarioRepository usuarioRepository;
    private final VacanteRepository vacanteRepository;
    private final CarreraRepository carreraRepository;
    private final ModalidadRepository modalidadRepository;
    private final HorarioRepository horarioRepository;

    public CoordinadorVacanteController(
            UsuarioRepository usuarioRepository,
            VacanteRepository vacanteRepository,
            CarreraRepository carreraRepository,
            ModalidadRepository modalidadRepository,
            HorarioRepository horarioRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.vacanteRepository = vacanteRepository;
        this.carreraRepository = carreraRepository;
        this.modalidadRepository = modalidadRepository;
        this.horarioRepository = horarioRepository;
    }

    // =========================================================
    // Helper: obtener usuario y área del coordinador
    // =========================================================
    private Usuario getUsuario(Authentication auth) {
        Usuario u = usuarioRepository.findByEmail(auth.getName());
        if (u == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        if (u.getArea() == null) {
            throw new RuntimeException("El usuario no tiene área asignada");
        }
        return u;
    }

    private AreaDgp getAreaCoordinador(Authentication auth) {
        return getUsuario(auth).getArea();
    }

    // =========================================================
    // 1) Obtener mi área (para frontend)
    // =========================================================
    @GetMapping("/mi-area")
    public ResponseEntity<?> miArea(Authentication auth) {
        AreaDgp area = getAreaCoordinador(auth);
        return ResponseEntity.ok(new Object() {
            public final Long idArea = area.getId();
            public final String nombre = area.getNombre();
        });
    }

    // =========================================================
    // 2) Listar solo mis vacantes
    // =========================================================
    @GetMapping("/getAll")
    public List<Vacante> getAll(Authentication auth) {
        AreaDgp area = getAreaCoordinador(auth);
        return vacanteRepository.findByAreasdgpIdarea_Id(area.getId());
    }

    // =========================================================
    // 3) Crear vacante (FORZAR área del coordinador)
    // =========================================================
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Vacante req, Authentication auth) {

        Usuario creador = getUsuario(auth);
        AreaDgp area = creador.getArea();

        // 🔒 Forzar área y creador
        req.setAreasdgpIdarea(area);
        req.setCreadoPor(creador);

        // Convertir hora si viene null
        if (req.getFechaPublicacion() == null) {
            return ResponseEntity.badRequest().body("Fecha de publicación requerida");
        }

        Vacante guardada = vacanteRepository.save(req);
        return ResponseEntity.ok(guardada);
    }

    // =========================================================
    // 4) Actualizar vacante (validar área)
    // =========================================================
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody Vacante req,
            Authentication auth
    ) {
        AreaDgp area = getAreaCoordinador(auth);

        if (!vacanteRepository.existsByIdAndAreasdgpIdarea_Id(id, area.getId())) {
            return ResponseEntity.status(403).body("No puedes editar vacantes de otra área");
        }

        req.setId(id);
        req.setAreasdgpIdarea(area);

        Vacante updated = vacanteRepository.save(req);
        return ResponseEntity.ok(updated);
    }

    // =========================================================
    // 5) Eliminar vacante (validar área)
    // =========================================================
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Authentication auth) {

        AreaDgp area = getAreaCoordinador(auth);

        if (!vacanteRepository.existsByIdAndAreasdgpIdarea_Id(id, area.getId())) {
            return ResponseEntity.status(403).body("No puedes eliminar vacantes de otra área");
        }

        vacanteRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
