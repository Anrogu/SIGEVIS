package com.proyecto.SsYPp.Service.Impl;
import com.proyecto.SsYPp.Entity.Usuario;
import com.proyecto.SsYPp.Repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 1. Busca el usuario en la base de datos usando el Repositorio
        Usuario usuario = usuarioRepository.findByEmail(email);
        // 2. Define las Autoridades/Roles (temporalmente, un solo rol)
        // NOTA: Para producción, este rol debería obtenerse de tu tabla 'roles' usando idrol.
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_ADMIN")
        );

        return new User(
                usuario.getEmail(),           // El nombre de usuario (el correo)
                usuario.getPassword(),        // La contraseña (DEBE estar cifrada con BCrypt en la BD)
                authorities                   // Los roles/permisos del usuario
        );
    }
}