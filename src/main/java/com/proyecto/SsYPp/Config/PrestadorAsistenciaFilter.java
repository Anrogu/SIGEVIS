package com.proyecto.SsYPp.Config;

import com.proyecto.SsYPp.Entity.Usuario;
import com.proyecto.SsYPp.Repository.PostulacionRepository;
import com.proyecto.SsYPp.Repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class PrestadorAsistenciaFilter extends OncePerRequestFilter {

    private final UsuarioRepository usuarioRepository;
    private final PostulacionRepository postulacionRepository;

    public PrestadorAsistenciaFilter(UsuarioRepository usuarioRepository,
                                     PostulacionRepository postulacionRepository) {
        this.usuarioRepository = usuarioRepository;
        this.postulacionRepository = postulacionRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // ✅ Aplicar solo a:
        // - vista: /usuario/asistencia
        // - API:  /asistencias/**
        boolean esAsistencia = path.startsWith("/usuario/asistencia") || path.startsWith("/asistencias/");
        if (!esAsistencia) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            filterChain.doFilter(request, response);
            return;
        }

        String email = auth.getName();
        Usuario u = usuarioRepository.findByEmail(email);

        if (u == null) {
            response.sendRedirect("/login");
            return;
        }

        // ✅ ACEPTADO = 3
        boolean aceptado = postulacionRepository
                .existsByUsuariosIdusuario_IdAndEstatusIdestatus_Id(u.getId(), 3L);

        if (!aceptado) {
            // manda a pantalla "pendiente"
            response.sendRedirect("/usuario/pendiente");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
