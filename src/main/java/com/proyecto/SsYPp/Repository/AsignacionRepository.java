package com.proyecto.SsYPp.Repository;


import com.proyecto.SsYPp.Entity.Asignacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {
}
