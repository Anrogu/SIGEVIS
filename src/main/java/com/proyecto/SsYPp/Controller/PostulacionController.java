package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Dto.PostulacionDto;
import com.proyecto.SsYPp.Service.PostulacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("postulaciones")
public class PostulacionController {
    @Autowired
    PostulacionService postulacionService;
    @GetMapping
    public PostulacionDto getPostulacion(@PathVariable Long postulacion){
        return postulacionService.get(postulacion);
    }
    @GetMapping("/getAll")
    public List<PostulacionDto> getPostulaciones(){
        return postulacionService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public String deletePostulaciones(@PathVariable Long id){ // Parameter changed from 'usuario' to 'id'
        postulacionService.delete(id);
        return "se ha borrado la postulacion";
    }

    @PutMapping("/update/{id}")
    public PostulacionDto updatePostulacion(
            @PathVariable Long id,
            @Valid @RequestBody PostulacionDto postulacion) {
        return postulacionService.update(postulacion);
    }
    @PostMapping("/create")
    public ResponseEntity<PostulacionDto> registerPostulacion(@Valid @RequestBody PostulacionDto postulacion) {
        PostulacionDto nuevaPostulacion = postulacionService.create(postulacion);
        return new ResponseEntity<>(nuevaPostulacion, HttpStatus.CREATED);

    }

}
