package com.edutech.microservicio_principal.Clase;

import com.edutech.microservicio_principal.Curso.Curso;
import com.edutech.microservicio_principal.Curso.CursoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // Configura un entorno de prueba JPA con una base de datos H2 en memoria
class ClaseRepositoryTest {

    @Autowired
    private ClaseRepository claseRepository; // Repositorio real inyectado por el contexto de prueba

    @Autowired
    private CursoRepository cursoRepository; // También se inyecta para persistir un curso relacionado

    @Test
    void testGuardarYRecuperarClase() {
        // Crear y guardar un curso necesario para establecer la relación con Clase
        Curso curso = new Curso();
        curso.setNombre("Curso de Python");
        curso.setDescripcion("Desde cero");
        curso = cursoRepository.save(curso); // Persiste el curso para que pueda ser usado en la clase

        // Crear y guardar una clase asociada al curso
        Clase clase = new Clase();
        clase.setNombre("Introducción a Python");
        clase.setDescripcion("Variables, estructuras, etc.");
        clase.setCuposDisponibles(25);
        clase.setHorario("Lunes 10:00 - 12:00");
        clase.setCurso(curso); // Relaciona la clase con el curso existente
        claseRepository.save(clase);

        // Recuperar todas las clases desde el repositorio y verificar su contenido
        List<Clase> clases = claseRepository.findAll();
        assertEquals(1, clases.size()); // Solo una clase registrada
        assertEquals("Introducción a Python", clases.get(0).getNombre()); // Nombre correcto
        assertEquals("Curso de Python", clases.get(0).getCurso().getNombre()); // Relación con curso verificada
    }
}