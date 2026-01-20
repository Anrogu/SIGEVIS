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
    private VacanteService vacanteService;

    @GetMapping("/{id}")
    public VacanteDto getVacante(@PathVariable Long id){
        return vacanteService.get(id);
    }

    @GetMapping("/getAll")
    public List<VacanteDto> getVacantes(){
        return vacanteService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteVacante(@PathVariable Long id){
        vacanteService.delete(id);
        return "Se ha borrado la vacanteIdvacante con éxito";
    }

    @PutMapping("/update/{id}")
    public VacanteDto updateVacante(@PathVariable Long id,
                                    @Valid @RequestBody VacanteDto vacante) {
        vacante.setIdVacantes(id);
        return vacanteService.update(vacante);
    }

    @PostMapping("/create")
    public ResponseEntity<VacanteDto> registerVacante(@Valid @RequestBody VacanteDto vacante) {
        VacanteDto nuevaVacante = vacanteService.create(vacante);
        return new ResponseEntity<>(nuevaVacante, HttpStatus.CREATED);
    }
}
