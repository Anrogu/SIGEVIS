package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Entity.Usuario;
import com.proyecto.SsYPp.Repository.UsuarioRepository;
import com.proyecto.SsYPp.Service.AsignacionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/coordinador")
public class AsignacionCoordinadorController {

    private final AsignacionService asignacionService;
    private final UsuarioRepository usuarioRepository;

    public AsignacionCoordinadorController(AsignacionService asignacionService,
                                           UsuarioRepository usuarioRepository) {
        this.asignacionService = asignacionService;
        this.usuarioRepository = usuarioRepository;
    }
    @GetMapping("/becarios-por-vacante/{vacanteId}")
    @ResponseBody
    public List<Usuario> obtenerBecarios(@PathVariable Long vacanteId){
        return asignacionService.obtenerBecariosPorVacante(vacanteId);
    }


    @GetMapping("/asignaciones")
    public String vistaAsignaciones(Authentication auth, Model model) {

        String email = auth.getName();

        // Tu repo regresa Usuario (no Optional)
        Usuario coordinador = usuarioRepository.findByEmail(email);
        if (coordinador == null) {
            throw new RuntimeException("Coordinador no encontrado con email: " + email);
        }

        if (coordinador.getArea() == null || coordinador.getArea().getId() == null) {
            throw new RuntimeException("El coordinador no tiene un área asignada.");
        }

        Long areaId = coordinador.getArea().getId();

        model.addAttribute("asignaciones",
                asignacionService.listarAsignacionesCoordinadorVista(areaId));

        return "coordinador/asignaciones";
    }
}
