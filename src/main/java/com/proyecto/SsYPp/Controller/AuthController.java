package com.proyecto.SsYPp.Controller;
import com.proyecto.SsYPp.Dto.UsuarioDto;
import com.proyecto.SsYPp.Service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gob/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUsuario(@RequestBody UsuarioDto usuarioDto) {
        try {
            if (usuarioService.existsByEmail(usuarioDto.getEmail())) {
                return ResponseEntity.badRequest().body("El correo electrónico ya está registrado.");
            }

            UsuarioDto nuevoUsuario = usuarioService.create(usuarioDto);
            return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al registrar: " + e.getMessage());
        }
    }

    @GetMapping("/perfil")
    public ResponseEntity<?> getAuthenticatedUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return new ResponseEntity<>("Usuario no autenticado", HttpStatus.UNAUTHORIZED);
        }


        String email = userDetails.getUsername();

        try {
            UsuarioDto usuarioDto = usuarioService.getUsuarioByEmail(email);
            return ResponseEntity.ok(usuarioDto);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Perfil no encontrado: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}