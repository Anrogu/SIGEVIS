package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Dto.AsignacionDto;
import com.proyecto.SsYPp.Service.AsignacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asignaciones")
public class AsignacionesController {
    @Autowired
    AsignacionService asignacionService;
    @GetMapping("/{asignaciones}")
    public AsignacionDto getAsignacion(@PathVariable Long asignacion){
        return asignacionService.get(asignacion);
    }
    @GetMapping("/getAll")
    public List<AsignacionDto> getAsignaciones(){
        return asignacionService.getAll();
    }
    @DeleteMapping("/delete/{id}")
    public String deleteAsignacion(@PathVariable Long id){
        asignacionService.delete(id);
        return "se ha borrado la Asignacion con exito";
    }

    @PutMapping("/update/{id}")
    public AsignacionDto updateAsignacion(
            @PathVariable Integer id,
            @Valid @RequestBody AsignacionDto asignacion) {
        return asignacionService.update(asignacion);
    }
    @PostMapping("/create")
    public ResponseEntity<AsignacionDto> registerAsignacion(@Valid @ModelAttribute AsignacionDto asignacion) {
        AsignacionDto nuevaAsignacion = asignacionService.create(asignacion);
        return new ResponseEntity<>(nuevaAsignacion, HttpStatus.CREATED);
    }
}
