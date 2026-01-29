package com.proyecto.SsYPp.Service.Impl;

import com.proyecto.SsYPp.Dto.ActividadDto;
import com.proyecto.SsYPp.Entity.Actividad; // Tu entidad JPA
import com.proyecto.SsYPp.Entity.Asignacion;
import com.proyecto.SsYPp.Entity.Usuario;
import com.proyecto.SsYPp.Repository.ActividadRepository;
import com.proyecto.SsYPp.Repository.AsignacionRepository;
import com.proyecto.SsYPp.Repository.UsuarioRepository;
import com.proyecto.SsYPp.Service.ActividadService;
import com.proyecto.SsYPp.Service.AsignacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActividadServiceImpl implements ActividadService {

    @Autowired
    private ActividadRepository actividadRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AsignacionRepository asignacionRepository;

    @Override
    @Transactional
    public ActividadDto create(ActividadDto actividadDto) {
        // 1. Convertir DTO a Entidad
        Actividad actividad = estaEntidad(actividadDto);
        // 2. Guardar
        Actividad nuevaActividad = actividadRepository.save(actividad);
        // 3. Retornar DTO
        return esteDto(nuevaActividad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActividadDto> getAll() {
        return actividadRepository.findAll()
                .stream()
                .map(this::esteDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ActividadDto get(Long id) {
        Actividad actividad = actividadRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada con id: " + id));
        return esteDto(actividad);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!actividadRepository.existsById(Math.toIntExact(id))) {
            throw new RuntimeException("No se puede eliminar: Actividad no encontrada");
        }
        actividadRepository.deleteById(Math.toIntExact(id));
    }

    @Override
    @Transactional
    public ActividadDto update(ActividadDto actividadDto) {
        // Verificar existencia antes de actualizar
        if (!actividadRepository.existsById(Math.toIntExact(actividadDto.getId()))) {
            throw new RuntimeException("No se puede actualizar: Actividad no existente");
        }

        Actividad actividadActualizada = actividadRepository.save(estaEntidad(actividadDto));
        return esteDto(actividadActualizada);
    }

    // --- Métodos Auxiliares de Conversión (Mappers) ---
    // Si usas ModelMapper o MapStruct, puedes sustituir esto.

    private ActividadDto esteDto(Actividad entidad) {
        ActividadDto dto = new ActividadDto();
        dto.setId(entidad.getId());
        dto.setDescripcion(entidad.getDescripcion());
        dto.setHorasDestinadas(entidad.getHorasDestinadas());
        dto.setFechaActividad(entidad.getFechaActividad());
        dto.setEstatusActividad(entidad.getEstatusActividad());
        dto.setTipoActividad(entidad.getTipoActividad());
        dto.setIdUsuario(entidad.getIdUsuario().getId()); // Relación ManyToOne
        return dto;
    }

    private Actividad estaEntidad(ActividadDto dto) {
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("usuario no encontrado"));

        Actividad entidad = new Actividad();
        entidad.setId(dto.getId());
        entidad.setDescripcion(dto.getDescripcion());
        entidad.setHorasDestinadas(dto.getHorasDestinadas());
        entidad.setFechaActividad(dto.getFechaActividad());
        entidad.setEstatusActividad(dto.getEstatusActividad());
        entidad.setTipoActividad(dto.getTipoActividad());
        entidad.setIdUsuario(usuario);
        // Aquí deberías buscar el usuario en el repo y setearlo si es necesario
        return entidad;
    }
}