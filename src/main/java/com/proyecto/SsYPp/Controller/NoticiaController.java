package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Dto.UsuarioDto;
import com.proyecto.SsYPp.Entity.Noticia;
import com.proyecto.SsYPp.Service.NoticiaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/noticias")
public class NoticiaController {
 @Autowired
 NoticiaService noticiaService;
 @GetMapping("/{noticia}")
 public Noticia getNoticia(@PathVariable Long noticia){
     return noticiaService.get(noticia);
 }
    @GetMapping("/getAll")
    public List<Noticia> getUsuarios(){
        return noticiaService.getAll();
    }
    @DeleteMapping("/delete/{id}")
    public String deleteNoticia(@PathVariable Long id){ // Parameter changed from 'usuario' to 'id'
        noticiaService.delete(id);
        return "se ha borrado el usuario";
    }

    @PutMapping("/update/{id}")
    public Noticia updateNoticia(
            @PathVariable Integer id,
            @Valid @RequestBody Noticia noticia) {
        return noticiaService.update(noticia);
    }
    public ResponseEntity<Noticia> registerNoticia(@Valid @ModelAttribute Noticia noticia) {
        Noticia nuevaNoticia = noticiaService.create(noticia);
        return new ResponseEntity<>(nuevaNoticia, HttpStatus.CREATED);

    }
}
