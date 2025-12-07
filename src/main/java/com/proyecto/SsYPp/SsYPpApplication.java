package com.proyecto.SsYPp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories; // <<< Importar esto

@SpringBootApplication
// Agrega esta línea para decirle a Spring dónde está tu repositorio:
@EnableJpaRepositories(basePackages = "com.proyecto.SsYPp.Repository")
public class SsYPpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsYPpApplication.class, args);
    }
}