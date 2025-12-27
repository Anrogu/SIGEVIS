package com.proyecto.SsYPp.Controller;

import org.springframework.web.bind.annotation.GetMapping;

public class AdminController {
    // El return "nombreArchivo" busca: src/main/resources/templates/nombreArchivo.html

    @GetMapping("/vacantes.html")
    public String vacantes() {
        return "vacantes"; // Busca templates/vacantes.html
    }

    @GetMapping("/asignaciones.html")
    public String asignaciones() {
        return "asignaciones"; // Busca templates/asignaciones.html
    }

    @GetMapping("/convenios.html")
    public String convenios() {
        return "convenios"; // Busca templates/convenios.html
    }

    @GetMapping("/usuarios.html")
    public String usuarios() {
        return "usuarios"; // Busca templates/usuarios.html
    }

    @GetMapping("/auditorias.html")
    public String auditorias() {
        return "auditorias"; // Busca templates/auditorias.html
    }

    @GetMapping("/catalogos.html")
    public String catalogos() {
        return "catalogos"; // Busca templates/catalogos.html
    }
}