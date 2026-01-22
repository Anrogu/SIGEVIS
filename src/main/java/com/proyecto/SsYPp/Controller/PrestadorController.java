package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Entity.Usuario;
import com.proyecto.SsYPp.Entity.Vacante;
import com.proyecto.SsYPp.Repository.UsuarioRepository;
import com.proyecto.SsYPp.Repository.VacanteRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/usuario")
public class PrestadorController {

    private final UsuarioRepository usuarioRepository;
    private final VacanteRepository vacanteRepository;

    public PrestadorController(UsuarioRepository usuarioRepository,
                               VacanteRepository vacanteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.vacanteRepository = vacanteRepository;
    }

    @GetMapping("/perfil")
    public String perfilPrestador(Authentication auth, Model model) {

        // 1) Usuario logueado (email)
        String email = auth.getName();

        // 2) Traer usuario desde BD
        Usuario usuario = usuarioRepository.findByEmail(email);

        // 3) Obtener carrera y área del perfil
        Long carreraId = null;
        if (usuario.getCarrerasIdcarrera() != null && usuario.getCarrerasIdcarrera().getId() != null) {
            // Carrera.id es Integer -> lo convertimos a Long
            carreraId = usuario.getCarrerasIdcarrera().getId().longValue();
        }

        Long areaId = null;
        if (usuario.getArea() != null && usuario.getArea().getId() != null) {
            // AreaDgp.id (probablemente Integer) -> lo convertimos a Long
            areaId = usuario.getArea().getId().longValue();
        }

        // 4) Consultar vacantes según perfil
        List<Vacante> vacantes;
        if (carreraId != null && areaId != null) {
            // Filtrar por carrera + área
            vacantes = vacanteRepository.findByCarrerasIdcarrera_IdAndAreasdgpIdarea_Id(carreraId, areaId);
        } else if (carreraId != null) {
            // Filtrar mínimo por carrera
            vacantes = vacanteRepository.findByCarrerasIdcarrera_Id(carreraId);
        } else {
            // Sin carrera asignada => lista vacía
            vacantes = List.of();
        }

        // 5) Mandar a la vista
        model.addAttribute("usuario", usuario);
        model.addAttribute("vacantes", vacantes);

        return "prestador/perfil";
    }
}
