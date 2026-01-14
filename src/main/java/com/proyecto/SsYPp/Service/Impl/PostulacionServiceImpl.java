package com.proyecto.SsYPp.Service.Impl;

import com.proyecto.SsYPp.Dto.NoticiaDto;
import com.proyecto.SsYPp.Dto.PostulacionDto;
import com.proyecto.SsYPp.Dto.StatusPostulacionDto;
import com.proyecto.SsYPp.Entity.*;
import com.proyecto.SsYPp.Repository.AsignacionRepository;
import com.proyecto.SsYPp.Repository.StatusPostulacionRepository;
import com.proyecto.SsYPp.Repository.UsuarioRepository;
import com.proyecto.SsYPp.Service.AsignacionService;
import com.proyecto.SsYPp.Service.PostulacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PostulacionServiceImpl implements PostulacionService {
    @Autowired
    PostulacionService postulacionService;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    private AsignacionRepository asignacionRepository;
    @Autowired
    StatusPostulacionRepository statusPostulacionRepository;

    @Override


    public PostulacionDto create(PostulacionDto postulacion) {
        return null;
    }

    @Override
    public List<PostulacionDto> getAll() {
        return List.of();
    }

    @Override
    public PostulacionDto get(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public PostulacionDto update(PostulacionDto postulacion) {
        return null;
    }
    private PostulacionDto convertirEntidadADTO(Postulacion postulacion) {

        PostulacionDto postulacionDto = new PostulacionDto();
        postulacionDto.setId(postulacion.getId());
        postulacionDto.setFechaPostulacion(postulacion.getFechaPostulacion());
        postulacionDto.setComentarios(postulacion.getComentarios());
        return postulacionDto;
    }
    private Postulacion convertirDTOAEntidad(PostulacionDto dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuariosIdusuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getUsuariosIdusuario()));
        Asignacion asignacion = asignacionRepository.findById(dto.getAsignaciones())
                .orElseThrow(() -> new RuntimeException("Asignacion no encontrada con ID: " + dto.getUsuariosIdusuario()));
        //StatusPostulacion status = statusPostulacionRepository.findById(Math.toIntExact(dto.getEstatusIdestatus()))
      //          .orElseThrow()
        Postulacion postulacion = new Postulacion();
        postulacion.setFechaPostulacion(dto.getFechaPostulacion());
        postulacion.setComentarios(dto.getComentarios());
        postulacion.setUsuariosIdusuario(usuario);
        postulacion.setAsignaciones(asignacion);

        return postulacion;
    }
}
