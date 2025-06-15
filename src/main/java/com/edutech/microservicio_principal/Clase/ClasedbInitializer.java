package com.edutech.microservicio_principal.Clase;

import com.edutech.microservicio_principal.Curso.Curso;
import com.edutech.microservicio_principal.Curso.CursoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ClasedbInitializer implements CommandLineRunner {

    private final ClaseRepository claseRepository;
    private final CursoRepository cursoRepository;

    public ClasedbInitializer(ClaseRepository claseRepository, CursoRepository cursoRepository) {
        this.claseRepository = claseRepository;
        this.cursoRepository = cursoRepository;
    }

    @Override
    public void run(String... args) {
        if (claseRepository.count() == 0) { // Para evitar duplicados en cada arranque

            List<Curso> cursos = cursoRepository.findAll(); // Obtener todos los cursos existentes

            for (Curso curso : cursos) {
                List<Clase> clasesPorDefecto = switch (curso.getNombre()) { // Asignar clases según el curso
                    case "Curso de Java" -> List.of(
                        new Clase(null, "POO en Java", "Principios de programación orientada a objetos en Java", 20, "Lunes 10:00 - 12:00", curso),
                        new Clase(null, "Colecciones en Java", "Uso de List, Set y Map en Java", 15, "Miércoles 14:00 - 16:00", curso)
                    );
                    case "Curso de Inglés Básico" -> List.of(
                        new Clase(null, "Inglés elemental I", "Aprende las reglas gramaticales esenciales", 25, "Martes 09:00 - 11:00", curso),
                        new Clase(null, "Inglés elemental II", "Mejora tu comunicación y amplía tus conocimientos", 30, "Jueves 16:00 - 18:00", curso)
                    );
                    case "Curso de Japonés" -> List.of(
                        new Clase(null, "Hiragana y Katakana", "Domina los alfabetos básicos del japonés", 20, "Viernes 10:00 - 12:00", curso),
                        new Clase(null, "Frases Comunes", "Aprende expresiones esenciales en japonés", 15, "Sábado 14:00 - 16:00", curso)
                    );
                    default -> List.of(); // En caso de que un curso no coincida
                };

                claseRepository.saveAll(clasesPorDefecto); // Guardar clases con su curso asociado
            }

            System.out.println("Clases iniciales cargadas en la base de datos.");
        }
    }
}