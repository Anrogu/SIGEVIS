package com.proyecto.SsYPp.Repository;

import com.proyecto.SsYPp.Entity.Contador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ContadorRepository extends JpaRepository<Contador, Long> {


    @Query("SELECT COALESCE(SUM(c.horasTotales),0) FROM Contador c WHERE c.idusuario.id = :idUsuario")
    BigDecimal totalHorasPorUsuario(@Param("idUsuario") Long idUsuario);
}