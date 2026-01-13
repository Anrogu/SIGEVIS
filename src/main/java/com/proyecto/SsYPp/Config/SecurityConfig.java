package com.proyecto.SsYPp.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.DisabledException;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomSuccessHandler customSuccessHandler;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(CustomSuccessHandler customSuccessHandler,
                          UserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder) {
        this.customSuccessHandler = customSuccessHandler;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
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
                        // =========================
                        // 1) RUTAS PÚBLICAS (SIN LOGIN)
                        // =========================
                        .requestMatchers(
                                "/",
                                "/public/**",
                                "/css/**", "/js/**", "/img/**",
                                "/login",
                                "/registro",
                                "/register",
                                "/public",

                                // ✅ APIs públicas para el perfil sin login
                                "/noticias/publicadas",
                                "/noticias/*"
                        ).permitAll()

                        // =========================
                        // 2) REGLAS POR ROL (TU SISTEMA PRIVADO)
                        // =========================
                        .requestMatchers("/index", "/index.html").hasAuthority("ROLE_1")
                        .requestMatchers("/vacantes", "/vacantes.html").hasAnyAuthority("ROLE_2", "ROLE_1")


                        // 3. Cualquier otra ruta requiere estar autenticado
                        .anyRequest().authenticated()
                )
                //---------------------------------------------------------------
                .authenticationProvider(authenticationProvider())

                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .successHandler(customSuccessHandler)

                        // AHORA: si el usuario está desactivado -> /login?disabled=true
                        // (y si es otro error -> /login?error=true)
                        .failureHandler((request, response, exception) -> {
                            if (exception instanceof DisabledException) {
                                response.sendRedirect("/login?disabled=true");
                            } else {
                                response.sendRedirect("/login?error=true");
                            }
                        })
                        // ---------------------------------------------------------------------------------
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true) // Limpia la sesión al salir
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )

                //-------------------------------------------------------------------------------------------
                //.exceptionHandling(ex -> ex.accessDeniedPage("/error/403"))
                .build();
    }
}