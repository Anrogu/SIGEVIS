package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Dto.UsuarioDto;
import com.proyecto.SsYPp.Entity.Usuario;
import com.proyecto.SsYPp.Service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{usuario}")
    public UsuarioDto getUsuarios(@PathVariable Integer usuario){
        return usuarioService.getUsuario(usuario);
    }

    @GetMapping("/getAll")
    public List<UsuarioDto> getUsuarios(){
        return usuarioService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUsuarios(@PathVariable Integer id){ // Parameter changed from 'usuario' to 'id'
        usuarioService.delete(id);
        return "se ha borrado el usuario";
    }

    @PutMapping("/update/{id}")
    public UsuarioDto updateUsuarios(
            @PathVariable Integer id,
            @Valid @RequestBody UsuarioDto usuarios) {
        return usuarioService.update(usuarios);
    }
    @GetMapping("/email/{email}")

        public UsuarioDto getEmail(@PathVariable String email){
        return usuarioService.getUsuarioByEmail(email);
    }
}