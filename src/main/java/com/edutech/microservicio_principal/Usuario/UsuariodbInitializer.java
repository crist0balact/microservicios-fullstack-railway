package com.edutech.microservicio_principal.Usuario;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuariodbInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;

    // Añadida clase para cargar usuarios por defecto a la base de datos

    public UsuariodbInitializer(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            List<Usuario> usuarios = List.of(
                new Usuario(null, "Sebastián", "Bravo", "seba@edutech.com", "clave123", "ESTUDIANTE"),
                new Usuario(null, "Cristóbal", "Cisternas", "cris@edutech.com", "segura456", "DOCENTE"),
                new Usuario(null, "Diego", "Donoso", "diego@edutech.com", "admin789", "ADMIN")
            );

            usuarioRepository.saveAll(usuarios);
            System.out.println("Usuarios por defecto cargados en la base de datos.");
        }
    }
}