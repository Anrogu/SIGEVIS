package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Entity.AreaDgp;
import com.proyecto.SsYPp.Repository.AreaDgpRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/areas")
public class AreaDgpController {

    private final AreaDgpRepository areaDgpRepository;

    public AreaDgpController(AreaDgpRepository areaDgpRepository) {
        this.areaDgpRepository = areaDgpRepository;
    }

    // GET: /admin/areas/getAll
    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AreaDgp> getAll() {
        return areaDgpRepository.findAll();
    }
}
