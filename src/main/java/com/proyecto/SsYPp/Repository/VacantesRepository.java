package com.proyecto.SsYPp.Repository;

import com.proyecto.SsYPp.Entity.Vacante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacantesRepository extends JpaRepository<Vacante, Long> {
}