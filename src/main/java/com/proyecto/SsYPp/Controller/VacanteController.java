package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Dto.VacanteDto;
import com.proyecto.SsYPp.Service.VacanteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/vacantes")

public class VacanteController {
    @Autowired
    VacanteService vacanteService;
    @GetMapping("/{vacantes}")
    public VacanteDto getVacante(@PathVariable Long vacantes){
        return vacanteService.get(vacantes);
    }
    @GetMapping("/getAll")
    public List<VacanteDto> getVacantes(){
        return vacanteService.getAll();
    }
    @DeleteMapping("/delete/{id}")
    public String deleteVacante(@PathVariable Long id){
        vacanteService.delete(id);
        return "se ha borrado la vacante con exito";
    }

    @PutMapping("/update/{id}")
    public VacanteDto updateVacante(
            @PathVariable Long id,
            @Valid @RequestBody VacanteDto vacante) {
        return vacanteService.update(vacante);
    }
    @PostMapping("/create")
    public ResponseEntity<VacanteDto> registerVacante(@Valid @ModelAttribute VacanteDto vacante) {
        VacanteDto nuevaVacante = vacanteService.create(vacante);

        return new ResponseEntity<>(nuevaVacante, HttpStatus.CREATED);
    }
}
