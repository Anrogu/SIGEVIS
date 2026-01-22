package com.proyecto.SsYPp.Repository;

import com.proyecto.SsYPp.Entity.Vacante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacanteRepository extends JpaRepository<Vacante, Long> {

    List<Vacante> findByAreasdgpIdarea_Id(Long areaId);

    boolean existsByIdAndAreasdgpIdarea_Id(Long idVacante, Long areaId);

    // ✅ PRESTADOR
    List<Vacante> findByCarrerasIdcarrera_Id(Long carreraId);

    List<Vacante> findByCarrerasIdcarrera_IdAndAreasdgpIdarea_Id(Long carreraId, Long areaId);
}
