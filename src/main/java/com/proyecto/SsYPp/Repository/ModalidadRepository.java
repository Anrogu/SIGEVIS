package com.proyecto.SsYPp.Repository;

import com.proyecto.SsYPp.Entity.Modalidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModalidadRepository extends JpaRepository<Modalidad, Long> {
}
