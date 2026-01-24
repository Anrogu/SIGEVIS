package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Dto.CreatePostulacionRequest;
import com.proyecto.SsYPp.Dto.PostulacionCoordinadorDetalleDto;
import com.proyecto.SsYPp.Dto.PostulacionCoordinadorRowDto;
import com.proyecto.SsYPp.Dto.PostulacionDto;
import com.proyecto.SsYPp.Service.PostulacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/postulaciones")
public class PostulacionController {

    @Autowired
    private PostulacionService postulacionService;

    @GetMapping("/get/{id}")
    public PostulacionDto getPostulacion(@PathVariable Long id) {
        return postulacionService.get(id);
    }

    @GetMapping("/getAll")
    public List<PostulacionDto> getPostulaciones() {
        return postulacionService.getAll();
    }

    // ✅ COORDINADOR: listado (tabla con nombres)
    @GetMapping("/coordinador/mis-postulaciones")
    public List<PostulacionCoordinadorRowDto> misPostulacionesCoordinador(Authentication authentication) {
        return postulacionService.misPostulacionesCoordinador(authentication.getName());
    }

    // ✅ COORDINADOR: detalle (comprobación)
    @GetMapping("/coordinador/detalle/{id}")
    public PostulacionCoordinadorDetalleDto detalleCoordinador(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return postulacionService.detalleCoordinador(id, authentication.getName());
    }

    // ✅ COORDINADOR: cambiar estatus (aceptar/rechazar)
    @PutMapping("/coordinador/{id}/estatus/{estatusId}")
    public ResponseEntity<Void> cambiarEstatus(
            @PathVariable Long id,
            @PathVariable Long estatusId,
            Authentication authentication
    ) {
        postulacionService.cambiarEstatus(id, estatusId, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public String deletePostulaciones(@PathVariable Long id) {
        postulacionService.delete(id);
        return "se ha borrado la postulacion";
    }

    @PutMapping("/update/{id}")
    public PostulacionDto updatePostulacion(
            @PathVariable Long id,
            @Valid @RequestBody PostulacionDto postulacion
    ) {
        postulacion.setId(id);
        return postulacionService.update(postulacion);
    }

    @PostMapping("/create")
    public ResponseEntity<PostulacionDto> registerPostulacion(@Valid @RequestBody PostulacionDto postulacion) {
        PostulacionDto nuevaPostulacion = postulacionService.create(postulacion);
        return new ResponseEntity<>(nuevaPostulacion, HttpStatus.CREATED);
    }

    // ✅ API JSON (cuando llamas desde fetch/axios)
    @PostMapping("/postular")
    public ResponseEntity<PostulacionDto> postular(
            @Valid @RequestBody CreatePostulacionRequest request,
            Authentication authentication
    ) {
        PostulacionDto postulacion = postulacionService.postular(
                request.getVacanteId(),
                request.getComentarios(),
                authentication.getName()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(postulacion);
    }

    // ✅ FORM POST (para Thymeleaf <form> normal)
    @PostMapping(value = "/postular-form", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Void> postularForm(
            @RequestParam("vacanteId") Long vacanteId,
            @RequestParam(value = "comentarios", required = false) String comentarios,
            Authentication authentication,
            @RequestHeader(value = "Referer", required = false) String referer
    ) {
        postulacionService.postular(vacanteId, comentarios, authentication.getName());

        // ✅ volver a la misma página (donde estaba el prestador)
        String redirectTo = (referer != null && !referer.isBlank())
                ? referer
                : "/"; // fallback por si no viene Referer

        // ✅ agregamos un mensaje por query param
        // (lo leemos en el HTML y lo mostramos)
        if (redirectTo.contains("?")) {
            redirectTo += "&msgPostulacion=ok";
        } else {
            redirectTo += "?msgPostulacion=ok";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectTo));
        return new ResponseEntity<>(headers, HttpStatus.FOUND); // 302
    }

}
