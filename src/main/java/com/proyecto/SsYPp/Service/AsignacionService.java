package com.proyecto.SsYPp.Service;

import com.proyecto.SsYPp.Dto.AsignacionDto;
import com.proyecto.SsYPp.Dto.NoticiaDto;
import com.proyecto.SsYPp.Entity.Postulacion;
import org.springframework.stereotype.Service;
import com.proyecto.SsYPp.Dto.AsignacionAdminRowDto;
import com.proyecto.SsYPp.Dto.AsignacionCoordinadorRowDto;
import java.util.List;
import com.proyecto.SsYPp.Entity.Usuario;
import java.util.List;

@Service
public interface AsignacionService {
    public AsignacionDto create(AsignacionDto asignacion);
    public List<AsignacionDto> getAll();
    public AsignacionDto get(Long id);
    public void delete (Long id);
    public AsignacionDto update(AsignacionDto asignacion);

    void crearAsignacionDesdePostulacion(Postulacion p);
    void crearAsignacionDesdePostulacionId(Long idPostulacion);

    List<AsignacionAdminRowDto> listarAsignacionesAdmin();
    List<AsignacionAdminRowDto> listarAceptadasParaVistaAdmin();
    List<AsignacionCoordinadorRowDto> listarAsignacionesCoordinadorVista(Long areaId);
    List<Usuario> obtenerBecariosPorVacante(Long vacanteId);
}

