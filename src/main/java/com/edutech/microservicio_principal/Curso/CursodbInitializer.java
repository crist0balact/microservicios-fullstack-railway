package com.edutech.microservicio_principal.Curso;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CursodbInitializer implements CommandLineRunner {

    private final CursoRepository cursoRepository;

    public CursodbInitializer(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @Override
    public void run(String... args) {
        if (cursoRepository.count() == 0) { // Para evitar duplicados en cada arranque
            List<Curso> cursosPorDefecto = List.of(
                new Curso(null, "Curso de Java", "Introducción a Java y POO", 19990),
                new Curso(null, "Curso de Inglés Básico", "Curso gratuito de Inglés Básico", 0),
                new Curso(null, "Curso de Japonés", "Aprende Japonés básico", 40000)
            
            );

            cursoRepository.saveAll(cursosPorDefecto);
            System.out.println("Cursos iniciales cargados en la base de datos.");
        }
    }
}