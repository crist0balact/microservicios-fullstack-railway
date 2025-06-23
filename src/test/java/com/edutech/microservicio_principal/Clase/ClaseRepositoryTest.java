package com.edutech.microservicio_principal.Clase;

import com.edutech.microservicio_principal.Curso.Curso;
import com.edutech.microservicio_principal.Curso.CursoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClaseRepositoryTest {

    @Autowired
    private ClaseRepository claseRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Test
    void testGuardarYRecuperarClase() {
        // Crear un curso
        Curso curso = new Curso();
        curso.setNombre("Curso de Python");
        curso.setDescripcion("Desde cero");
        curso = cursoRepository.save(curso);

        // Crear una clase
        Clase clase = new Clase();
        clase.setNombre("Introducción a Python");
        clase.setDescripcion("Variables, estructuras, etc.");
        clase.setCuposDisponibles(25);
        clase.setHorario("Lunes 10:00 - 12:00");
        clase.setCurso(curso);
        claseRepository.save(clase);

        // Verificar
        List<Clase> clases = claseRepository.findAll();
        assertEquals(1, clases.size());
        assertEquals("Introducción a Python", clases.get(0).getNombre());
        assertEquals("Curso de Python", clases.get(0).getCurso().getNombre());
    }
}