package com.proyecto.SsYPp.Repository;

import com.proyecto.SsYPp.Entity.StatusPostulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusPostulacionRepository extends JpaRepository<StatusPostulacion,Integer> {
}
