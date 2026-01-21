package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Dto.NoticiaDto;
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
    public String deleteNoticia(@PathVariable Long id){ // Parameter changed from 'usuario' to 'id'
        noticiaService.delete(id);
        return "se ha borrado la noticia";
    }

    @PutMapping("/update/{id}")
    public NoticiaDto updateNoticia(
            @PathVariable Long id,
            @Valid @RequestBody NoticiaDto noticia) {
        return noticiaService.update(noticia);
    }
    @PostMapping("/create")
    public ResponseEntity<NoticiaDto> registerNoticia(@Valid @ModelAttribute NoticiaDto noticia) {
        NoticiaDto nuevaNoticia = noticiaService.create(noticia);
        return new ResponseEntity<>(nuevaNoticia, HttpStatus.CREATED);

    }
}
