package com.proyecto.SsYPp.Service.Impl;

import com.proyecto.SsYPp.Dto.AsignacionDto;
import com.proyecto.SsYPp.Entity.*;
import com.proyecto.SsYPp.Repository.*;
import com.proyecto.SsYPp.Service.AsignacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsignacionServiceImpl implements AsignacionService {
    @Autowired
    private AsignacionRepository asignacionRepository;
    @Autowired
    private VacanteRepository vacantesRepository;
    @Autowired
    private PostulacionRepository postulacionRepository;
    @Override

    public AsignacionDto create(AsignacionDto asignacionDto) {
        Asignacion asignacion = convertirDTOAEntidad(asignacionDto);
        Asignacion guardada = asignacionRepository.save(asignacion);
        return convertirEntidadADTO(guardada);
    }

    @Override
    public List<AsignacionDto> getAll() {
        List<Asignacion> asignaciones = asignacionRepository.findAll();
        return asignaciones.stream()
                .map(this::convertirEntidadADTO)
                .collect(Collectors.toList());
    }

    @Override
    public AsignacionDto get(Long id) {
        Asignacion asignacion = asignacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("asignacion no encontrada"));
        return convertirEntidadADTO(asignacion);
    }

    @Override
    public void delete(Long id) {
    asignacionRepository.deleteById(id);
    }

    @Override
    public AsignacionDto update(AsignacionDto asignacionDto) {
        Asignacion asignacion =  convertirDTOAEntidad(asignacionDto);
        Asignacion actualizada = asignacionRepository.save(asignacion);
        return convertirEntidadADTO(actualizada);
    }
    public AsignacionDto convertirEntidadADTO(Asignacion  asignacion) {
        AsignacionDto asignacionDto=new AsignacionDto();
        asignacionDto.setId(asignacion.getId());
        asignacionDto.setFechaFin(asignacion.getFechaFin());
        asignacionDto.setFechaInicio(asignacion.getFechaInicio());
        asignacionDto.setPostulacionesIdpostulacion(asignacion.getPostulacionesIdpostulacion().getId());
        asignacionDto.setVacantesIdvacante(asignacion.getVacantesIdvacante().getId());
        return asignacionDto;

    }

    public Asignacion convertirDTOAEntidad(AsignacionDto dto) {
        Postulacion postulacion = postulacionRepository.findById(dto.getPostulacionesIdpostulacion())
                .orElseThrow(() -> new RuntimeException("postulacion no encontrada"));
        Vacante vacante = vacantesRepository.findById(dto.getVacantesIdvacante())
                .orElseThrow(() -> new RuntimeException("vacante no encontrada"));

        Asignacion asignacion = new Asignacion();

        if (dto.getId() != null) {
            asignacion.setId(dto.getId());
        }

        asignacion.setFechaFin(dto.getFechaFin());
        asignacion.setFechaInicio(dto.getFechaInicio());
        asignacion.setPostulacionesIdpostulacion(postulacion);
        asignacion.setVacantesIdvacante(vacante);

        return asignacion;
    }

    @Override
    public void crearAsignacionDesdePostulacion(Postulacion p) {
        Asignacion nueva = new Asignacion();

        // 1. Relaciones (Usando los nombres exactos de tus entidades)
        nueva.setPostulacionesIdpostulacion(p);

        // En tu entidad Asignacion tienes 'vacantesIdvacante', lo llenamos desde la postulación
        nueva.setVacantesIdvacante(p.getVacanteIdvacante());

        // 2. Área (Corrigiendo el nombre del getter)
        // En Vacante.java tienes: private AreaDgp areasdgpIdarea;
        // Lombok genera: getAreasdgpIdarea()
        if(p.getVacanteIdvacante().getAreasdgpIdarea() != null) {
            // Asumiendo que Asignacion tiene un campo 'area' o similar.
            // Si no lo tienes en Asignacion.java, borra estas 3 lineas.
            // nueva.setArea(p.getVacanteIdvacante().getAreasdgpIdarea());
        }

        // 3. Fechas (OffsetTime)
        // Tu entidad usa OffsetTime, así que usamos OffsetTime.now()
        nueva.setFechaInicio(OffsetTime.now());

        // Ponemos fecha fin tentativa (ej. misma hora) o null si tu BD lo permite
        nueva.setFechaFin(OffsetTime.now().plusHours(1));

        asignacionRepository.save(nueva);
    }


}
