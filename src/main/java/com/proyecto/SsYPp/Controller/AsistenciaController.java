package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Entity.Asistencia;
import com.proyecto.SsYPp.Entity.Usuario;
import com.proyecto.SsYPp.Repository.UsuarioRepository;
import com.proyecto.SsYPp.Service.AsistenciaService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/asistencias")
public class AsistenciaController {

    private final AsistenciaService asistenciaService;
    private final UsuarioRepository usuarioRepository;

    public AsistenciaController(AsistenciaService asistenciaService,
                                UsuarioRepository usuarioRepository) {
        this.asistenciaService = asistenciaService;
        this.usuarioRepository = usuarioRepository;
    }

    private Long getUsuarioId(Authentication auth) {
        String email = auth.getName(); // login usa usernameParameter("email")
        Usuario u = usuarioRepository.findByEmail(email);

        if (u == null) {
            throw new RuntimeException("Usuario no encontrado: " + email);
        }
        return u.getId();
    }

    // ✅ POST /asistencias/entrada
    @PostMapping("/entrada")
    public Asistencia entrada(Authentication auth) {
        return asistenciaService.registrarEntrada(getUsuarioId(auth));
    }

    // ✅ POST /asistencias/salida
    @PostMapping("/salida")
    public Asistencia salida(Authentication auth) {
        return asistenciaService.registrarSalida(getUsuarioId(auth));
    }

    // ✅ GET /asistencias/total
    @GetMapping("/total")
    public BigDecimal totalHoras(Authentication auth) {
        return asistenciaService.totalHoras(getUsuarioId(auth));
    }
}
