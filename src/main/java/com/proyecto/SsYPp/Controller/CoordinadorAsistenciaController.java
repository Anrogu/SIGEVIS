package com.proyecto.SsYPp.Controller;

import com.proyecto.SsYPp.Dto.PrestadorAsistenciaRowDto;
import com.proyecto.SsYPp.Entity.Asistencia;
import com.proyecto.SsYPp.Service.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coordinador/asistencias")
public class CoordinadorAsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    // ✅ 1. Endpoint para llenar la tabla (GET)
    @GetMapping("/prestadores-hoy")
    public ResponseEntity<List<PrestadorAsistenciaRowDto>> getPrestadoresHoy(Authentication auth) {
        try {
            // Llama al servicio que ya arreglamos para traer la lista filtrada por área
            List<PrestadorAsistenciaRowDto> lista = asistenciaService.obtenerListaParaCoordinador(auth.getName());
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // ✅ 2. Endpoint para registrar ENTRADA (POST)
    @PostMapping("/entrada/{usuarioId}")
    public ResponseEntity<?> registrarEntrada(@PathVariable Long usuarioId) {
        try {
            // Guardamos la respuesta del servicio en una variable
            Asistencia asistenciaGuardada = asistenciaService.registrarEntrada(usuarioId);

            // Devolvemos el OBJETO completo (JSON), no un texto
            return ResponseEntity.ok(asistenciaGuardada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ 3. Endpoint para registrar SALIDA (POST)
    @PostMapping("/salida/{usuarioId}")
    public ResponseEntity<?> registrarSalida(@PathVariable Long usuarioId) {
        try {
            Asistencia asistenciaGuardada = asistenciaService.registrarSalida(usuarioId);

            return ResponseEntity.ok(asistenciaGuardada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}