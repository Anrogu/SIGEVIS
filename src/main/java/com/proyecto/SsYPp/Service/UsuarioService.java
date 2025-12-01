package com.proyecto.SsYPp.Service;
import com.proyecto.SsYPp.Entity.Usuario;
import com.proyecto.SsYPp.Dto.UsuarioDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsuarioService {

    public List<UsuarioDto> getAll();
    public UsuarioDto getUsuario(Integer id);
    public UsuarioDto getUsuarioByEmail(String email);
    public void delete(Integer id);

    boolean existsByEmail(String email);

    public UsuarioDto create(UsuarioDto usuario);
    public UsuarioDto update(UsuarioDto usuario);

}
