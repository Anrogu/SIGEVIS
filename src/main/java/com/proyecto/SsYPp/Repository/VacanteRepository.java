package com.proyecto.SsYPp.Repository;

import com.proyecto.SsYPp.Entity.Vacante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface VacanteRepository extends JpaRepository<Vacante, Long> {

    List<Vacante> findByAreasdgpIdarea_Id(Long areaId);

    boolean existsByIdAndAreasdgpIdarea_Id(Long idVacante, Long areaId);

    @Query("""
        SELECT v
        FROM Vacante v
        WHERE v.estatus = true
        AND (
            v.fechaVencimiento IS NULL
            OR v.fechaVencimiento >= CURRENT_DATE
        )
        AND v.carrerasIdcarrera.id = :carreraId
    """)
    List<Vacante> findVacantesActivasByCarrera(Long carreraId);

    @Query("""
        SELECT v
        FROM Vacante v
        WHERE v.estatus = true
        AND (
            v.fechaVencimiento IS NULL
            OR v.fechaVencimiento >= CURRENT_DATE
        )
        AND v.carrerasIdcarrera.id = :carreraId
        AND v.areasdgpIdarea.id = :areaId
    """)
    List<Vacante> findVacantesActivasByCarreraAndArea(Long carreraId, Long areaId);

    @Query("""
        SELECT v
        FROM Vacante v
        WHERE v.estatus = true
        AND (
            v.fechaVencimiento IS NULL
            OR v.fechaVencimiento >= CURRENT_DATE
        )
    """)
    List<Vacante> findVacantesActivas();
}