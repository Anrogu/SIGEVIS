package com.proyecto.SsYPp.Service.Impl;

import com.proyecto.SsYPp.Dto.PostulacionDto;
import com.proyecto.SsYPp.Entity.Asignacion;
import com.proyecto.SsYPp.Entity.Postulacion;
import com.proyecto.SsYPp.Entity.StatusPostulacion;
import com.proyecto.SsYPp.Entity.Usuario;
import com.proyecto.SsYPp.Repository.PostulacionRepository;
import com.proyecto.SsYPp.Repository.StatusPostulacionRepository;
import com.proyecto.SsYPp.Repository.UsuarioRepository;
import com.proyecto.SsYPp.Service.PostulacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostulacionServiceImpl implements PostulacionService {
    @Autowired
    private PostulacionRepository postulacionRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private StatusPostulacionRepository statusPostulacionRepository;

    @Override
    public PostulacionDto create(PostulacionDto postulacion) {
        Postulacion asignacion = convertirDTOAEntidad(postulacion);
        Postulacion guardada = postulacionRepository.save(asignacion);
        return convertirEntidadADTO(guardada);
    }

    @Override
    public List<PostulacionDto> getAll() {

        List<Postulacion> postulaciones = postulacionRepository.findAll();
        return postulaciones.stream()
                .map(this::convertirEntidadADTO)
                .collect(Collectors.toList());
    }

    @Override
    public PostulacionDto get(Long id) {
        Postulacion postulacion = postulacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("postulacion no encontrada"));
        return convertirEntidadADTO(postulacion);
    }

    @Override
    public void delete(Long id) {
        postulacionRepository.deleteById(id);
    }

    @Override
    public PostulacionDto update(PostulacionDto postulacion) {
        Postulacion postulacionDto =  convertirDTOAEntidad(postulacion);
        Postulacion actualizada = postulacionRepository.save(postulacionDto);
        return convertirEntidadADTO(actualizada);
    }

    public PostulacionDto convertirEntidadADTO(Postulacion postulacion) {
        PostulacionDto postulacionDto = new PostulacionDto();
        postulacionDto.setId(postulacion.getId());
        postulacionDto.setFechaPostulacion(postulacion.getFechaPostulacion());
        postulacionDto.setComentarios(postulacion.getComentarios());
        postulacionDto.setUsuariosIdusuario(postulacion.getUsuariosIdusuario().getId());
        postulacionDto.setEstatusIdestatus(postulacion.getEstatusIdestatus().getId());
        return postulacionDto;
    }
    public Postulacion convertirDTOAEntidad (PostulacionDto postulacionDto) {
        Usuario usuario = usuarioRepository.findById(postulacionDto.getUsuariosIdusuario())
                .orElseThrow(() -> new RuntimeException("usuario no encontrado"));
        StatusPostulacion status = statusPostulacionRepository.findById(Math.toIntExact(postulacionDto.getEstatusIdestatus().shortValue()))
                .orElseThrow(() -> new RuntimeException("usuario no encontrado"));
        Postulacion postulacion = new Postulacion();
        postulacion.setId(postulacionDto.getId());
        postulacion.setComentarios(postulacionDto.getComentarios());
        postulacion.setFechaPostulacion(OffsetTime.now());
        postulacion.setEstatusIdestatus(status);
        postulacion.setUsuariosIdusuario(usuario);
        return postulacion;
    }
}
