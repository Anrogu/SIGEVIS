package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Dto.AsistenciaHistorialRowDto;
import com.proyecto.SsYPp.Dto.AsistenciaHoyDto; // ✅ NUEVO
import com.proyecto.SsYPp.Entity.Asistencia;
import com.proyecto.SsYPp.Entity.Usuario;
import com.proyecto.SsYPp.Repository.ContadorRepository;
import com.proyecto.SsYPp.Repository.UsuarioRepository;
import com.proyecto.SsYPp.Service.AsistenciaService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/asistencia")
public class AsistenciaController {

    private final AsistenciaService asistenciaService;
    private final UsuarioRepository usuarioRepository;
    private final ContadorRepository contadorRepository;

    public AsistenciaController(AsistenciaService asistenciaService,
                                UsuarioRepository usuarioRepository,
                                ContadorRepository contadorRepository) {
        this.asistenciaService = asistenciaService;
        this.usuarioRepository = usuarioRepository;
        this.contadorRepository = contadorRepository;
    }

    private Long getUsuarioId(Authentication auth) {
        String email = auth.getName(); // login usa usernameParameter("email")
        Usuario u = usuarioRepository.findByEmail(email);

        if (u == null) {
            throw new RuntimeException("Usuario no encontrado: " + email);
        }
        return u.getId();
    }

    // =========================================================
    // ✅ (OPCIONAL / TRANSICIÓN)
    // Si ya vas a mover el registro al coordinador,
    // lo ideal es quitar estos POST para el prestador.
    // =========================================================

    /*
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
    */

    // ✅ GET /asistencias/total
    @GetMapping("/total")
    public BigDecimal totalHoras(Authentication auth) {
        return asistenciaService.totalHoras(getUsuarioId(auth));
    }

    // ✅ GET /asistencias/historial
    @GetMapping("/historial")
    public List<AsistenciaHistorialRowDto> historial(Authentication auth) {
        Long usuarioId = getUsuarioId(auth);

        return contadorRepository.findByIdusuario_IdOrderByFechaDesc(usuarioId)
                .stream()
                .map(c -> new AsistenciaHistorialRowDto(
                        c.getFecha(),
                        c.getIdasistencia().getHoraEntrada(),
                        c.getIdasistencia().getHoraSalida(),
                        c.getHorasTotales()
                ))
                .toList();
    }

    // ✅ NUEVO: GET /asistencias/hoy (estatus del día, SOLO lectura)
    @GetMapping("/hoy")
    public AsistenciaHoyDto hoy(Authentication auth) {
        return asistenciaService.obtenerEstatusHoy(getUsuarioId(auth));
    }
}
