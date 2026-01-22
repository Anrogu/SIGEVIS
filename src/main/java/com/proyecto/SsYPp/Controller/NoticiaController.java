package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Dto.NoticiaDto;
import com.proyecto.SsYPp.Service.NoticiaService;
import com.proyecto.SsYPp.Service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/noticias")
public class NoticiaController {

    @Autowired
    NoticiaService noticiaService;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/{noticia}")
    public NoticiaDto getNoticia(@PathVariable Long noticia){
        return noticiaService.get(noticia);
    }

    @GetMapping("/getAll")
    public List<NoticiaDto> getNoticias(){
        return noticiaService.getAll();
    }

    @GetMapping("/publicadas")
    public List<NoticiaDto> getNoticiasPublicadas() {
        return noticiaService.getPublicadas();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteNoticia(@PathVariable Long id){
        noticiaService.delete(id);
        return "se ha borrado la noticia";
    }

    @PutMapping("/update/{id}")
    public NoticiaDto updateNoticia(
            @PathVariable Long id,
            @Valid @RequestBody NoticiaDto noticia,
            Principal principal
    ) {
        // ✅ asegurar que el id del path se use
        noticia.setId(id);

        // ✅ si no viene autor, lo asignamos desde el usuario autenticado
        if (noticia.getAutoridIdusuario() == null) {
            String email = principal.getName();
            Integer idUsuario = usuarioService.getIdUsuarioByEmail(email);
            noticia.setAutoridIdusuario(idUsuario);
        }

        return noticiaService.update(noticia);
    }

    @PostMapping("/create")
    public ResponseEntity<NoticiaDto> registerNoticia(
            @Valid @ModelAttribute NoticiaDto noticia,
            Principal principal
    ) {
        // ✅ autor desde sesión
        String email = principal.getName();
        Integer idUsuario = usuarioService.getIdUsuarioByEmail(email);
        noticia.setAutoridIdusuario(idUsuario);

        NoticiaDto nuevaNoticia = noticiaService.create(noticia);
        return new ResponseEntity<>(nuevaNoticia, HttpStatus.CREATED);
    }
}
