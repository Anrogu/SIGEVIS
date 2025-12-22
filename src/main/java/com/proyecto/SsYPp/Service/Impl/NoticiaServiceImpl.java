package com.proyecto.SsYPp.Service.Impl;

import com.proyecto.SsYPp.Dto.NoticiaDto;
import com.proyecto.SsYPp.Dto.UsuarioDto;
import com.proyecto.SsYPp.Entity.Noticia;
import com.proyecto.SsYPp.Entity.Usuario;
import com.proyecto.SsYPp.Repository.NoticiaRepository;
import com.proyecto.SsYPp.Repository.UsuarioRepository;
import com.proyecto.SsYPp.Service.NoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticiaServiceImpl implements NoticiaService {
    @Autowired
    NoticiaRepository noticiaRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public NoticiaDto create(NoticiaDto noticiaDto) {
        Noticia noticia = convertirDTOAEntidad(noticiaDto);
        Noticia guardada = noticiaRepository.save(noticia);
        return convertirEntidadADTO(guardada);
    }

    @Override
    public List<NoticiaDto> getAll() {
        List<Noticia> usuarios = noticiaRepository.findAll();
        return usuarios.stream()
                .map(this::convertirEntidadADTO)
                .collect(Collectors.toList());
    }


    @Override
    public NoticiaDto get(Long id) {
        Noticia noticia = noticiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("noticia no encontrada"));
        return convertirEntidadADTO(noticia);
    }

    @Override
    public void delete(Long id) {
        noticiaRepository.deleteById(id);

    }

    @Override
    public NoticiaDto update(NoticiaDto noticiaDto) {
        Noticia noticia = convertirDTOAEntidad(noticiaDto);
        Noticia actualizada = noticiaRepository.save(noticia);
        return convertirEntidadADTO(actualizada);
    }

    private NoticiaDto convertirEntidadADTO(Noticia noticia) {
        NoticiaDto noticiaDto = new NoticiaDto();
        noticiaDto.setId(noticia.getId());
        noticiaDto.setTitulo(noticia.getTitulo());
        noticiaDto.setContenido(noticia.getContenido());
        noticiaDto.setEstatus(noticia.getEstatus());
        noticiaDto.setFechaPublicacion(noticia.getFechaPublicacion());
        noticiaDto.setAutoridIdusuario(noticia.getAutoridIdusuario().getId().intValue());
        noticiaDto.setNombreAutor(noticia.getAutoridIdusuario().getNombre());

        return noticiaDto;
    }
    private Noticia convertirDTOAEntidad(NoticiaDto dto) {
        Usuario usuario = usuarioRepository.findById(dto.getAutoridIdusuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getAutoridIdusuario()));
        Noticia noticia = new Noticia();
        noticia.setId(dto.getId());
        noticia.setTitulo(dto.getTitulo());
        noticia.setContenido(dto.getContenido());
        noticia.setEstatus(dto.getEstatus());
        noticia.setFechaPublicacion(dto.getFechaPublicacion());
        noticia.setAutoridIdusuario(usuario);

        return noticia;
    }
}
