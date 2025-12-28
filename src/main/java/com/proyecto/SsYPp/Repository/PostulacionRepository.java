package com.proyecto.SsYPp.Repository;

import com.proyecto.SsYPp.Entity.Postulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostulacionRepository extends JpaRepository<Postulacion, Long> {
}
