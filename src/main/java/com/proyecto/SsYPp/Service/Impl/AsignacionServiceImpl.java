package com.proyecto.SsYPp.Service.Impl;

import com.proyecto.SsYPp.Dto.AsignacionAdminRowDto;
import com.proyecto.SsYPp.Dto.AsignacionCoordinadorRowDto;
import com.proyecto.SsYPp.Dto.AsignacionDto;
import com.proyecto.SsYPp.Entity.Asignacion;
import com.proyecto.SsYPp.Entity.Postulacion;
import com.proyecto.SsYPp.Entity.Usuario;
import com.proyecto.SsYPp.Entity.Vacante;
import com.proyecto.SsYPp.Repository.AsignacionRepository;
import com.proyecto.SsYPp.Repository.PostulacionRepository;
import com.proyecto.SsYPp.Repository.VacanteRepository;
import com.proyecto.SsYPp.Service.AsignacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;


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
        Asignacion asignacion = convertirDTOAEntidad(asignacionDto);
        Asignacion actualizada = asignacionRepository.save(asignacion);
        return convertirEntidadADTO(actualizada);
    }

    // ✅ Tabla global del ADMIN (basada en tabla Asignaciones)
    @Override
    public List<AsignacionAdminRowDto> listarAsignacionesAdmin() {
        return asignacionRepository.findAllAdminRows();
    }

    // ✅ Vista ADMIN de "Asignaciones" basada en Postulaciones ACEPTADAS (status='A')
    // (trae todas las aceptadas aunque aún no exista fila en Asignaciones)
    @Override
    public List<AsignacionAdminRowDto> listarAceptadasParaVistaAdmin() {
        return postulacionRepository.findAceptadasParaAsignacionesAdminVista();
    }

    // ✅ Enriquecemos el DTO para mostrar nombres en la tabla (cuando sí hay Asignación)
    public AsignacionDto convertirEntidadADTO(Asignacion asignacion) {
        AsignacionDto dto = new AsignacionDto();

        dto.setId(asignacion.getId());
        dto.setFechaFin(asignacion.getFechaFin());
        dto.setFechaInicio(asignacion.getFechaInicio());

        // IDs
        if (asignacion.getPostulacionesIdpostulacion() != null) {
            dto.setPostulacionesIdpostulacion(asignacion.getPostulacionesIdpostulacion().getId());
        }

        if (asignacion.getVacantesIdvacante() != null) {
            dto.setVacantesIdvacante(asignacion.getVacantesIdvacante().getId());
        }

        // ====== EXTRA: Prestador, Vacante y Área ======
        Postulacion p = asignacion.getPostulacionesIdpostulacion();
        Vacante v = asignacion.getVacantesIdvacante();

        // Prestador (Postulación -> Usuario)
        if (p != null && p.getUsuariosIdusuario() != null) {
            Usuario u = p.getUsuariosIdusuario();

            String nombreCompleto =
                    safe(u.getNombre()) + " " +
                            safe(u.getPrimerapellido()) + " " +
                            safe(u.getSegundoapellido());

            dto.setPrestadorNombre(nombreCompleto.trim());
            dto.setPrestadorEmail(u.getEmail());
        }

        // Vacante
        if (v != null) {
            dto.setVacanteNombre(v.getNombrePuesto());

            // Área
            if (v.getAreasdgpIdarea() != null) {
                dto.setAreaNombre(v.getAreasdgpIdarea().getNombre());
            }
        }

        return dto;
    }

    private String safe(String s) {
        return s == null ? "" : s;
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

        // ===== OBTENER VACANTE =====
        Vacante vacante = p.getVacanteIdvacante();

        Integer ocupadas = vacante.getPlazasOcupadas();
        Integer total = vacante.getNumeroPlazas();

        if (ocupadas == null) {
            ocupadas = 0;
        }

        // ===== VALIDAR SI YA ESTÁ LLENA =====
        if (ocupadas >= total) {
            throw new RuntimeException("La vacante ya no tiene plazas disponibles.");
        }

        // ===== CREAR ASIGNACIÓN =====
        Asignacion nueva = new Asignacion();

        nueva.setPostulacionesIdpostulacion(p);
        nueva.setVacantesIdvacante(vacante);

        nueva.setFechaInicio(OffsetTime.now());

        // temporal
        nueva.setFechaFin(OffsetTime.now().plusHours(1));

        asignacionRepository.save(nueva);

        // ===== AUMENTAR PLAZAS OCUPADAS =====
        vacante.setPlazasOcupadas(ocupadas + 1);

        // ===== SI YA SE LLENÓ, CERRAR VACANTE =====
        if ((ocupadas + 1) >= total) {
            vacante.setEstatus(false);
        }

        vacantesRepository.save(vacante);
    }

    @Override
    public void crearAsignacionDesdePostulacionId(Long idPostulacion) {
        Postulacion p = postulacionRepository.findById(idPostulacion)
                .orElseThrow(() -> new RuntimeException("postulacion no encontrada"));

        crearAsignacionDesdePostulacion(p);
    }
    // ✅ Vista COORDINADOR de "Asignaciones" basada en Postulaciones ACEPTADAS (status='A') filtradas por área
    @Override
    public List<AsignacionCoordinadorRowDto> listarAsignacionesCoordinadorVista(Long areaId) {
        return postulacionRepository.findAceptadasParaAsignacionesCoordinadorVista(areaId);
    }
    public List<Usuario> obtenerBecariosPorVacante(Long vacanteId){

        List<Asignacion> asignaciones =
                asignacionRepository.findByVacantesIdvacante_IdAndActivoTrue(vacanteId);

        List<Usuario> usuarios = new ArrayList<>();

        for(Asignacion a : asignaciones){
            usuarios.add(
                    a.getPostulacionesIdpostulacion().getUsuariosIdusuario()
            );
        }

        return usuarios;
    }


}
