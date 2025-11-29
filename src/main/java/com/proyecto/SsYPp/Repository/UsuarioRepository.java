package com.proyecto.SsYPp.Repository;
import com.proyecto.SsYPp.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    Usuario findByEmail(String email);
}
