package com.proyecto.SsYPp.Service.Impl;

import com.proyecto.SsYPp.Dto.AsignacionDto;
import com.proyecto.SsYPp.Entity.Asignacion;
import com.proyecto.SsYPp.Entity.Postulacion;
import com.proyecto.SsYPp.Entity.Usuario;
import com.proyecto.SsYPp.Repository.AsignacionRepository;
import com.proyecto.SsYPp.Repository.PostulacionRepository;
import com.proyecto.SsYPp.Repository.UsuarioRepository;
import com.proyecto.SsYPp.Service.AsignacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsignacionServiceImpl implements AsignacionService {
    @Autowired
    private AsignacionRepository asignacionRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
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
        asignacionDto.setEstatus(asignacion.getEstatus());
        asignacionDto.setUsuariosIdusuario(Math.toIntExact(asignacion.getUsuariosIdusuario().getId()));
        asignacionDto.setPostulacionesIdpostulacion(Math.toIntExact(asignacion.getPostulacionesIdpostulacion().getId()));
        return asignacionDto;

    }
    public Asignacion convertirDTOAEntidad(AsignacionDto dto) {
        Usuario usuario= usuarioRepository.findById(dto.getUsuariosIdusuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Postulacion postulacion= postulacionRepository.findById(Long.valueOf(dto.getPostulacionesIdpostulacion()))
                .orElseThrow(() -> new RuntimeException("Postulacion no encontrada"));

        Asignacion asignacion = new Asignacion();
        asignacion.setFechaFin(dto.getFechaFin());
        asignacion.setFechaInicio(dto.getFechaInicio());
        asignacion.setEstatus(dto.getEstatus());
        asignacion.setId(Long.valueOf(dto.getId()));
        asignacion.setUsuariosIdusuario(usuario);
        asignacion.setPostulacionesIdpostulacion(postulacion);
        return asignacion;
    }
}
