package com.proyecto.SsYPp.Repository;
import com.proyecto.SsYPp.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
    @Query("SELECT u FROM Usuario u JOIN FETCH u.idrol WHERE u.email = :email")
    Usuario findByEmail(String email);
}
