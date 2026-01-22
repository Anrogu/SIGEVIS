package com.proyecto.SsYPp.Repository;

import com.proyecto.SsYPp.Entity.Vacante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacanteRepository extends JpaRepository<Vacante, Long> {

    // ✅ Listar vacantes por área (para coordinador)
    List<Vacante> findByAreasdgpIdarea_Id(Long areaId);

    // ✅ Validar rápido si una vacante pertenece a un área (para update/delete)
    boolean existsByIdAndAreasdgpIdarea_Id(Long idVacante, Long areaId);
}
