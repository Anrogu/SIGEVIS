package com.proyecto.SsYPp.Repository;

import com.proyecto.SsYPp.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u " +
            "JOIN FETCH u.idrol " +
            "LEFT JOIN FETCH u.area " +
            "WHERE u.email = :email")
    Usuario findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);
}