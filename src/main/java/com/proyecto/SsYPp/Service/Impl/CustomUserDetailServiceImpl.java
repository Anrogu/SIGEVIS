package com.proyecto.SsYPp.Service.Impl;
import com.proyecto.SsYPp.Entity.Rol;
import com.proyecto.SsYPp.Entity.Usuario;
import com.proyecto.SsYPp.Repository.RolRepository;
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

    public CustomUserDetailServiceImpl(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) throw new UsernameNotFoundException("Usuario no encontrado");

        // Mapeamos el ID numérico de tu tabla al formato ROLE_X
        String authority = "ROLE_" + usuario.getIdrol().getId();

        // Aquí es donde se aplica el bloqueo por status.
        // Si status = false -> disabled(true) -> Spring lanza DisabledException al intentar login.
        boolean deshabilitado = (usuario.getStatus() == null) ? true : !usuario.getStatus();


        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .authorities(authority)

                // ✅ NUEVO:
                // ANTES no había validación de status, por eso entraban aunque estuvieran desactivados.
                .disabled(deshabilitado)

                .build();
    }
}