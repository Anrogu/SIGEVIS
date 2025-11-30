package com.proyecto.SsYPp.Service.Impl;
import com.proyecto.SsYPp.Entity.Rol;
import com.proyecto.SsYPp.Entity.Carrera;
import com.proyecto.SsYPp.Entity.Usuario;
import com.proyecto.SsYPp.Dto.UsuarioDto;
import com.proyecto.SsYPp.Repository.CarreraRepository;
import com.proyecto.SsYPp.Repository.RolRepository;
import com.proyecto.SsYPp.Repository.UsuarioRepository;
import com.proyecto.SsYPp.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder; // <--- Importante: Importación de PasswordEncoder
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<UsuarioDto> getAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(this::convertirEntidadADTO)
                .collect(Collectors.toList());

    }
    @Override
    public UsuarioDto getUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertirEntidadADTO(usuario);
    }

    @Override
    public UsuarioDto getUsuarioByEmail(String email) {
        Usuario usuario= usuarioRepository.findByEmail(email);
        return convertirEntidadADTO(usuario);
    }

    @Override
    public void delete(Integer id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public UsuarioDto create(UsuarioDto usuarioDto) {
        String rawPassword = usuarioDto.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        usuarioDto.setPassword(encodedPassword);
        Usuario usuario = convertirDTOaEntidad(usuarioDto);
        Usuario guardado = usuarioRepository.save(usuario);
        return convertirEntidadADTO(guardado);
    }

    @Override
    public UsuarioDto update(UsuarioDto usuarioDto) {
        if (usuarioDto.getNombre() == null) {
            throw new IllegalArgumentException("El ID es obligatorio para actualizar");
        }
        String rawPassword = usuarioDto.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        usuarioDto.setPassword(encodedPassword);
        Usuario usuario = convertirDTOaEntidad(usuarioDto);
        Usuario actualizado = usuarioRepository.save(usuario);
        return convertirEntidadADTO(actualizado);
    }
    private UsuarioDto convertirEntidadADTO(Usuario usuario) {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setIdrol(usuario.getIdrol().getId().intValue());
        usuarioDto.setCarrerasIdcarrera(usuario.getCarrerasIdcarrera().getId().intValue());
        usuarioDto.setNombre(usuario.getNombre());
        usuarioDto.setPrimerapellido(usuario.getPrimerapellido());
        usuarioDto.setSegundoapellido(usuario.getSegundoapellido());
        usuarioDto.setEmail(usuario.getEmail());
        usuarioDto.setStatus(usuario.getStatus());
        usuarioDto.setPassword(usuario.getPassword()); // Aquí va la contraseña encriptada

        return usuarioDto;
    }

    // Metodo Dto a Entidad
    private Usuario convertirDTOaEntidad(UsuarioDto dto) {
        Rol rol = rolRepository.findById(dto.getIdrol())
                .orElseThrow(() -> new RuntimeException("Rol con ID " + dto.getIdrol() + " no existe."));
        Carrera carrera = carreraRepository.findById(dto.getCarrerasIdcarrera())
                .orElseThrow(() -> new RuntimeException("carrera no encontrada"));

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setPrimerapellido(dto.getPrimerapellido());
        usuario.setSegundoapellido(dto.getSegundoapellido());
        usuario.setEmail(dto.getEmail());
        usuario.setStatus(dto.getStatus());
        usuario.setPassword(dto.getPassword());
        usuario.setIdrol(rol);
        usuario.setCarrerasIdcarrera(carrera);
        return usuario;
    }

}