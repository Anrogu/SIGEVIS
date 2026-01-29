package com.proyecto.SsYPp.Repository;

import com.proyecto.SsYPp.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("""
        SELECT u FROM Usuario u
        JOIN FETCH u.idrol
        LEFT JOIN FETCH u.area
        LEFT JOIN FETCH u.carrerasIdcarrera
        WHERE u.email = :email
    """)
    Usuario findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);

    // ✅ NUEVO: Prestadores/Usuarios aceptados (status=true) por área del coordinador
    @Query("""
        SELECT u FROM Usuario u
        JOIN FETCH u.idrol r
        LEFT JOIN FETCH u.area a
        LEFT JOIN FETCH u.carrerasIdcarrera c
        WHERE a.id = :areaId
          AND UPPER(r.nombre) IN ('USUARIO', 'PRESTADOR')
          AND u.status = true
        ORDER BY u.nombre ASC, u.primerapellido ASC
    """)
    List<Usuario> findPrestadoresAceptadosPorArea(@Param("areaId") Long areaId);
}
