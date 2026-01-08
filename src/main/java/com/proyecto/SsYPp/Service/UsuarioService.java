package com.proyecto.SsYPp.Service;
import com.proyecto.SsYPp.Entity.Usuario;
import com.proyecto.SsYPp.Dto.UsuarioDto;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public interface UsuarioService {

    Page<UsuarioDto> getAll(Pageable pageable);    public UsuarioDto getUsuario(Integer id);
    public UsuarioDto getUsuarioByEmail(String email);
    public void delete(Integer id);
    boolean existsByEmail(String email);
    public UsuarioDto create(UsuarioDto usuario);
    public UsuarioDto update(UsuarioDto usuario);

    // ✅ NUEVO: activar/desactivar
    boolean toggleStatus(Integer id);
}