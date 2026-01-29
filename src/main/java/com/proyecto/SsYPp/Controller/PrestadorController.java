package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Dto.ActividadDto;
import com.proyecto.SsYPp.Dto.ActividadDtoMapping;
import com.proyecto.SsYPp.Dto.PostulacionPrestadorRowDto;
import com.proyecto.SsYPp.Dto.VacanteDto;
import com.proyecto.SsYPp.Entity.Usuario;
import com.proyecto.SsYPp.Repository.ActividadRepository;
import com.proyecto.SsYPp.Repository.PostulacionRepository;
import com.proyecto.SsYPp.Repository.UsuarioRepository;
import com.proyecto.SsYPp.Service.VacanteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/usuario")
public class PrestadorController {

    private final UsuarioRepository usuarioRepository;
    private final VacanteService vacanteService;
    private final PostulacionRepository postulacionRepository;
    private final ActividadRepository actividadRepository;

    public PrestadorController(UsuarioRepository usuarioRepository,
                               VacanteService vacanteService,
                               PostulacionRepository postulacionRepository, ActividadRepository actividadRepository) {
        this.usuarioRepository = usuarioRepository;
        this.vacanteService = vacanteService;
        this.postulacionRepository = postulacionRepository;
        this.actividadRepository = actividadRepository;
    }

    @GetMapping("/perfil")
    public String perfilPrestador(Authentication auth, Model model) {

        String email = auth.getName();
        Usuario usuario = usuarioRepository.findByEmail(email);

        List<VacanteDto> vacantes =
                vacanteService.getVacantesParaPrestadorSinPostuladas(usuario.getId());

        model.addAttribute("usuario", usuario);
        model.addAttribute("vacantes", vacantes);

        return "prestador/perfil";
    }

    @GetMapping("/postulaciones")
    public String misPostulaciones(Authentication auth, Model model) {

        String email = auth.getName();
        Usuario usuario = usuarioRepository.findByEmail(email);

        List<PostulacionPrestadorRowDto> postulaciones =
                postulacionRepository.findRowsByUsuario(usuario.getId());

        model.addAttribute("usuario", usuario);
        model.addAttribute("postulaciones", postulaciones);

        return "prestador/postulaciones";
    }
    @GetMapping("/actividades")
    public String misActividades(Authentication auth, Model model) {

        String email = auth.getName();
        Usuario usuario = usuarioRepository.findByEmail(email);

        List<ActividadDtoMapping> actividades =
                actividadRepository.findRowsByUsuario(usuario.getId());

        model.addAttribute("usuario", usuario);
        model.addAttribute("postulaciones", actividades);

        return "prestador/actividades";
    }
}
