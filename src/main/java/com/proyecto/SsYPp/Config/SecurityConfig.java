package com.proyecto.SsYPp.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomSuccessHandler customSuccessHandler;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    // ✅ NUEVO: filtro para bloquear asistencia si no está aceptado
    private final PrestadorAsistenciaFilter prestadorAsistenciaFilter;

    public SecurityConfig(CustomSuccessHandler customSuccessHandler,
                          UserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder,
                          PrestadorAsistenciaFilter prestadorAsistenciaFilter) {
        this.customSuccessHandler = customSuccessHandler;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.prestadorAsistenciaFilter = prestadorAsistenciaFilter;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // públicas
                        .requestMatchers(
                                "/",
                                "/public/**",
                                "/css_public/**", "/css_admin/**", "/js/**", "/img/**",
                                "/login",
                                "/registro",
                                "/register",
                                "/public",
                                "/gob/auth/**",
                                "/noticias/publicadas",
                                "/noticias/*"
                        ).permitAll()

                        // ✅ EXCEPCIÓN: permitir "asignar" a ADMIN y COORDINADOR
                        .requestMatchers("/admin/asignaciones/asignar/**")
                        .hasAnyAuthority("ADMIN", "COORDINADOR")

                        // ADMIN
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")

                        // COORDINADOR
                        .requestMatchers("/coordinador/**").hasAuthority("COORDINADOR")

                        // USUARIO
                        .requestMatchers("/usuario/**").hasAuthority("USUARIO")

                        .requestMatchers("/coordinador/asistencias/**").hasAuthority("COORDINADOR")
                        .requestMatchers("/coordinador/prestadores/**").hasAuthority("COORDINADOR")

                        .anyRequest().authenticated()
                )

                .authenticationProvider(authenticationProvider())

                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .successHandler(customSuccessHandler)
                        .failureHandler((request, response, exception) -> {
                            if (exception instanceof DisabledException) {
                                response.sendRedirect("/login?disabled=true");
                            } else {
                                response.sendRedirect("/login?error=true");
                            }
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )

                // ✅ AQUÍ ENTRA EL FILTRO (bloquea /usuario/asistencia y /asistencias/** si no está aceptado)
                .addFilterBefore(prestadorAsistenciaFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }
}
