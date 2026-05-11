package com.proyecto.SsYPp.Service.Impl;

import com.proyecto.SsYPp.Entity.Rol;
import com.proyecto.SsYPp.Entity.Carrera;
import com.proyecto.SsYPp.Entity.Usuario;
import com.proyecto.SsYPp.Dto.UsuarioDto;
import com.proyecto.SsYPp.Entity.Asignacion;
import com.proyecto.SsYPp.Repository.CarreraRepository;
import com.proyecto.SsYPp.Repository.RolRepository;
import com.proyecto.SsYPp.Repository.UsuarioRepository;
import com.proyecto.SsYPp.Repository.AsignacionRepository;
import com.proyecto.SsYPp.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class UsuarioImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CarreraRepository carreraRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Override
    public Page<UsuarioDto> getAll(Pageable pageable) {
        Page<Usuario> usuariosPage = usuarioRepository.findAll(pageable);
        return usuariosPage.map(this::convertirEntidadADTO);
    }

    @Override
    public UsuarioDto getUsuario(Integer id) {
        // Corrección: Convertir Integer a Long
        Usuario usuario = usuarioRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertirEntidadADTO(usuario);
    }

    @Override
    public UsuarioDto getUsuarioByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        return convertirEntidadADTO(usuario);
    }

    @Override
    public void delete(Integer id) {
        // Corrección: Convertir Integer a Long
        usuarioRepository.deleteById(Long.valueOf(id));
    }

    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public UsuarioDto create(UsuarioDto usuarioDto) {
        if (existsByEmail(usuarioDto.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico " + usuarioDto.getEmail() + " ya está registrado.");
        }
        if (usuarioDto.getStatus() == null) {
            usuarioDto.setStatus(true);
        }

        // Encriptar contraseña nueva
        String rawPassword = usuarioDto.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        usuarioDto.setPassword(encodedPassword);

        Usuario usuario = convertirDTOAEntidad(usuarioDto);
        Usuario guardado = usuarioRepository.save(usuario);
        return convertirEntidadADTO(guardado);
    }

    @Override
    public UsuarioDto update(UsuarioDto usuarioDto) {
        // 1. Obtener el usuario REAL de la base de datos
        if (usuarioDto.getIdusuario() == null) {
            throw new IllegalArgumentException("El ID es obligatorio para actualizar");
        }

        Long idLong = Long.valueOf(usuarioDto.getIdusuario());
        Usuario usuarioExistente = usuarioRepository.findById(idLong)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. CORRECCIÓN ERROR CORREO:
        // Solo falla si el correo es diferente al actual Y ya existe en otro lado
        if (!usuarioExistente.getEmail().equals(usuarioDto.getEmail())
                && existsByEmail(usuarioDto.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico " + usuarioDto.getEmail() + " ya está registrado.");
        }

        // 3. Actualizar datos básicos
        usuarioExistente.setNombre(usuarioDto.getNombre());
        usuarioExistente.setPrimerapellido(usuarioDto.getPrimerapellido());
        usuarioExistente.setSegundoapellido(usuarioDto.getSegundoapellido());
        usuarioExistente.setEmail(usuarioDto.getEmail());
        usuarioExistente.setStatus(usuarioDto.getStatus());

        // 4. Actualizar Rol
        if (usuarioDto.getIdrol() != null) {
            Rol rol = rolRepository.findById(usuarioDto.getIdrol())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
            usuarioExistente.setIdrol(rol);
        }

        // 5. CORRECCIÓN CONTRASEÑA:
        // Solo la cambiamos si el usuario escribió algo nuevo. Si viene vacío, dejamos la vieja.
        if (usuarioDto.getPassword() != null && !usuarioDto.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(usuarioDto.getPassword());
            usuarioExistente.setPassword(encodedPassword);
        }

        // 6. Guardar el usuario existente actualizado
        Usuario actualizado = usuarioRepository.save(usuarioExistente);
        return convertirEntidadADTO(actualizado);
    }
    // ✅ NUEVO METODO (para Activar/Desactivar desde el Controller)
    @Override
    public boolean toggleStatus(Integer id) {

        // ANTES: el controller solo redireccionaba y no se actualizaba BD.
        // ✅ Ahora: alternamos status y devolvemos el valor final.

        Long idLong = Long.valueOf(id);

        Usuario usuario = usuarioRepository.findById(idLong)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));

        // Alterna: true->false, false->true
        boolean nuevoStatus = (usuario.getStatus() == null) ? true : !usuario.getStatus();
        usuario.setStatus(nuevoStatus);

        usuarioRepository.save(usuario);

        return nuevoStatus;
    }
    @Override
    public Integer getIdUsuarioByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado con email: " + email);
        }
        // tu entidad Usuario tiene getId() (Long)
        return usuario.getId().intValue();
    }
    @Override
    public List<UsuarioDto> getUsuariosAceptados() {

        List<Usuario> usuarios = usuarioRepository.findUsuariosConAsignacion();

        return usuarios.stream().map(u -> {
            UsuarioDto dto = new UsuarioDto();
            dto.setIdusuario(u.getId().intValue());
            dto.setNombre(u.getNombre());
            dto.setPrimerapellido(u.getPrimerapellido());
            dto.setEmail(u.getEmail());
            return dto;
        }).toList();
    }
    // --- MÉTODOS AUXILIARES ---

    private UsuarioDto convertirEntidadADTO(Usuario usuario) {
        UsuarioDto usuarioDto = new UsuarioDto();
        // Convertir Long a Integer para el DTO
        if (usuario.getId() != null) {
            usuarioDto.setIdusuario(usuario.getId().intValue());
        }

        if (usuario.getIdrol() != null) {
            usuarioDto.setIdrol(usuario.getIdrol().getId().intValue());
        }

        // Validación por si no tiene carrera
        if (usuario.getCarrerasIdcarrera() != null) {
            usuarioDto.setCarrerasIdcarrera(usuario.getCarrerasIdcarrera().getId().intValue());
        }

        usuarioDto.setNombre(usuario.getNombre());
        usuarioDto.setPrimerapellido(usuario.getPrimerapellido());
        usuarioDto.setSegundoapellido(usuario.getSegundoapellido());
        usuarioDto.setEmail(usuario.getEmail());
        usuarioDto.setStatus(usuario.getStatus());
        // No devolvemos la contraseña real por seguridad
        usuarioDto.setPassword(usuario.getPassword());

        return usuarioDto;
    }

    private Usuario convertirDTOAEntidad(UsuarioDto dto) {
        Usuario usuario = new Usuario();

        // Si es update, deberíamos setear el ID, pero este método se usa más para create
        // usuario.setId(Long.valueOf(dto.getIdusuario()));

        usuario.setNombre(dto.getNombre());
        usuario.setPrimerapellido(dto.getPrimerapellido());
        usuario.setSegundoapellido(dto.getSegundoapellido());
        usuario.setEmail(dto.getEmail());
        usuario.setStatus(dto.getStatus());
        usuario.setPassword(dto.getPassword());

        // Buscar Rol
        if (dto.getIdrol() != null) {
            Rol rol = rolRepository.findById(dto.getIdrol())
                    .orElseThrow(() -> new RuntimeException("Rol con ID " + dto.getIdrol() + " no existe."));
            usuario.setIdrol(rol);
        }

        // Buscar Carrera (Manejo de opcionalidad)
        if (dto.getCarrerasIdcarrera() != null) {
            Carrera carrera = carreraRepository.findById(dto.getCarrerasIdcarrera())
                    .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));
            usuario.setCarrerasIdcarrera(carrera);
        }

        return usuario;
    }
}