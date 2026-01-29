package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Dto.AsistenciaHoyDto;
import com.proyecto.SsYPp.Dto.PrestadorAsistenciaRowDto;
import com.proyecto.SsYPp.Entity.Usuario;
import com.proyecto.SsYPp.Repository.UsuarioRepository;
import com.proyecto.SsYPp.Service.AsistenciaService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coordinador/prestadores")
public class CoordinadorPrestadoresController {

    private final UsuarioRepository usuarioRepository;
    private final AsistenciaService asistenciaService;

    public CoordinadorPrestadoresController(UsuarioRepository usuarioRepository,
                                            AsistenciaService asistenciaService) {
        this.usuarioRepository = usuarioRepository;
        this.asistenciaService = asistenciaService;
    }

    private Usuario getCoordinador(Authentication auth) {
        Usuario u = usuarioRepository.findByEmail(auth.getName());
        if (u == null) throw new RuntimeException("Coordinador no encontrado");
        return u;
    }

    @GetMapping("/aceptados")
    public List<PrestadorAsistenciaRowDto> aceptados(Authentication auth) {

        Usuario coord = getCoordinador(auth);

        // ✅ AQUÍ ESTABA EL ERROR: es getArea(), no getAreasdgpIdarea()
        if (coord.getArea() == null) {
            throw new RuntimeException("El coordinador no tiene área asignada");
        }

        Long areaId = coord.getArea().getId();



        List<Usuario> prestadores = usuarioRepository.findPrestadoresAceptadosPorArea(areaId);

        return prestadores.stream().map(p -> {
            String nombreCompleto =
                    (p.getNombre() == null ? "" : p.getNombre()) + " " +
                            (p.getPrimerapellido() == null ? "" : p.getPrimerapellido()) + " " +
                            (p.getSegundoapellido() == null ? "" : p.getSegundoapellido());

            String carrera = (p.getCarrerasIdcarrera() != null)
                    ? p.getCarrerasIdcarrera().getNombre()
                    : "—";

            AsistenciaHoyDto hoy = asistenciaService.obtenerEstatusHoy(p.getId());

            return new PrestadorAsistenciaRowDto(
                    p.getId(),
                    nombreCompleto.trim(),
                    p.getEmail(),
                    carrera,
                    hoy
            );
        }).toList();
    }
}
