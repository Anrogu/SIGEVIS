package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Dto.ActividadDto; // Asegúrate de tener este DTO creado
import com.proyecto.SsYPp.Dto.UsuarioDto;
import com.proyecto.SsYPp.Repository.ActividadRepository;
import com.proyecto.SsYPp.Service.ActividadService; // Asegúrate de tener este Servicio
import com.proyecto.SsYPp.Service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/coordinador/actividades")
public class ActividadesController {

    @Autowired
    private ActividadService actividadService;

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ActividadRepository actividadRepository;
    // Obtener una actividad por ID
    @GetMapping("/{id}")
    public ActividadDto getActividad(@PathVariable Long id) {
        return actividadService.get(id);
    }

    // Listar todas las actividades
    @GetMapping("/getAll")
    public List<ActividadDto> getActividades() {
        return actividadService.getAll();
    }

    // Eliminar una actividad
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteActividad(@PathVariable Long id) {
        actividadService.delete(id);
        return ResponseEntity.ok("La actividad ha sido eliminada correctamente");
    }

    // Actualizar una actividad existente
    @PutMapping("/update/{id}")
    public ActividadDto updateActividad(
            @PathVariable Long id,
            @Valid @RequestBody ActividadDto actividadDto,
            Principal principal
    ) {
        // Aseguramos que el ID del path sea el que se actualice
        actividadDto.setId(id);

        // Lógica de auditoría o autoría similar a la de noticias
        if (actividadDto.getIdUsuario() == null) {
            String email = principal.getName();
            Integer idUsuario = usuarioService.getIdUsuarioByEmail(email);
            actividadDto.setIdUsuario(Long.valueOf(idUsuario));
        }

        return actividadService.update(actividadDto);
    }

    // Crear una nueva actividad
    @PostMapping("/create")
    public ResponseEntity<ActividadDto> createActividad(
            @Valid @RequestBody ActividadDto actividadDto,
            Principal principal
    ) {

        ActividadDto nuevaActividad = actividadService.create(actividadDto);
        return new ResponseEntity<>(nuevaActividad, HttpStatus.CREATED);
    }
    @PutMapping("/update-status/{id}")
    public ResponseEntity<?> updateStatusOnly(@PathVariable Long id) {
        try {
            // Buscamos si existe la actividad primero (opcional)
            actividadRepository.updateEstatus(id, "COMPLETADA");
            return ResponseEntity.ok().body("{\"message\": \"Actividad marcada como completada\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"No se pudo actualizar el estatus\"}");
        }
    }
    //listado de activiades
    @GetMapping("/becarios-aceptados")
    @ResponseBody
    public List<UsuarioDto> getBecariosAceptados() {
        return usuarioService.getUsuariosAceptados();
    }
}