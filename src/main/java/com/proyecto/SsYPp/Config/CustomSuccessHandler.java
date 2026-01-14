package com.proyecto.SsYPp.Config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // DEBUGGING
        System.out.println("AUTORIDADES ACTUALES: " + authentication.getAuthorities());

        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("");

        String redirectURL = switch (role) {
            case "ROLE_1" -> "/index";
            case "ROLE_2" -> "/usuarios";
            case "ROLE_3" -> "/vacantes";
            default       -> "/login?error=true";
        };

        response.sendRedirect(request.getContextPath() + redirectURL);
    }
}