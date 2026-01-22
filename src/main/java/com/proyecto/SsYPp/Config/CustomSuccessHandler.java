package com.proyecto.SsYPp.Config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // DEBUG
        System.out.println("AUTORIDADES ACTUALES: " + authentication.getAuthorities());

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        boolean isCoordinador = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("COORDINADOR"));

        boolean isUsuario = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("USUARIO"));

        String redirectURL;
        if (isAdmin) {
            redirectURL = "/admin/index";
        } else if (isCoordinador) {
            redirectURL = "/coordinador/index";
        } else if (isUsuario) {
            redirectURL = "/usuario/perfil";
        } else {
            redirectURL = "/login?error=true";
        }

        // Respeta contextPath (útil si no está en /)
        response.sendRedirect(request.getContextPath() + redirectURL);
    }
}
