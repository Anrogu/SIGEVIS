package com.proyecto.SsYPp.Repository;

import com.proyecto.SsYPp.Dto.AsignacionAdminRowDto;
import com.proyecto.SsYPp.Entity.Asignacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {

    @Query("""
        select new com.proyecto.SsYPp.Dto.AsignacionAdminRowDto(
            a.id,
            a.fechaInicio,
            a.fechaFin,
            concat(u.nombre, ' ', u.primerapellido, ' ', u.segundoapellido),
            v.nombrePuesto
        )
        from Asignacion a
        join a.postulacionesIdpostulacion p
        join p.usuariosIdusuario u
        join a.vacantesIdvacante v
        order by a.id desc
    """)
    List<AsignacionAdminRowDto> findAllAdminRows();

    List<Asignacion> findByVacantesIdvacante_AreasdgpIdarea_Id(Long areaId);
}