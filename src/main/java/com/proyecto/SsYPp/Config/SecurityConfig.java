package com.proyecto.SsYPp.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Autorización y Manejo de Rutas
                .authorizeHttpRequests(authorize -> authorize
                        // Rutas públicas que no requieren autenticación
                        .requestMatchers(
                                "/",                      // La URL raíz
                                "/login",                 // La vista de login (GET)
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/api/usuarios/create/**" // Ruta de registro pública
                        ).permitAll()

                        // Cualquier otra URL REQUIERE autenticación (protección)
                        .anyRequest().authenticated()
                )

                // 2. Configuración del Formulario de Login (activa el POST /login)
                .formLogin(form -> form
                        // URL de la vista de login (GET)
                        .loginPage("/login")
                        // URL a la que se envían las credenciales (POST)
                        // Esto crea el endpoint POST /login
                        .loginProcessingUrl("/login")
                        // Redirigir a /index después de un login exitoso
                        .defaultSuccessUrl("/index", true)
                        // Redirigir a /login en caso de fallo
                        .failureUrl("/login?error=true")
                        .permitAll()
                )

                // 3. Configuración de Logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )

                // 4. Deshabilita CSRF para pruebas de API/Postman
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}