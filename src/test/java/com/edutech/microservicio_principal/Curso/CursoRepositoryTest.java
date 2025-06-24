package com.edutech.microservicio_principal.Curso;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // Habilita pruebas con JPA usando una base de datos en memoria
class CursoRepositoryTest {

    @Autowired
    private CursoRepository cursoRepository; // Inyección del repositorio real

    @Test
    void testGuardarYBuscarCurso() {
        // Crear y guardar un curso
        Curso curso = new Curso();
        curso.setNombre("Curso de Kotlin");
        curso.setDescripcion("Programación moderna");
        curso.setValorCurso(22000);

        cursoRepository.save(curso);

        // Recuperar todos los cursos de la base y verificar resultados
        List<Curso> todos = cursoRepository.findAll();
        assertEquals(1, todos.size());
        assertEquals("Curso de Kotlin", todos.get(0).getNombre());
    }

    @Test
    void testFindByNombre() {
        // Guardar un curso con un nombre específico
        Curso curso = new Curso();
        curso.setNombre("Curso de Spring Boot");
        curso.setDescripcion("Microservicios en Java");
        curso.setValorCurso(18000);

        cursoRepository.save(curso);

        // Usar el método personalizado findByNombre() para buscar el curso
        Optional<Curso> encontrado = cursoRepository.findByNombre("Curso de Spring Boot");

        // Verificar que el curso fue encontrado y sus datos son correctos
        assertTrue(encontrado.isPresent());
        assertEquals("Microservicios en Java", encontrado.get().getDescripcion());
    }

    @Test
    void testFindByNombreNoExiste() {
        // Buscar un curso que no fue guardado
        Optional<Curso> resultado = cursoRepository.findByNombre("No existe");

        // Verificar que no se encontró ningún curso
        assertFalse(resultado.isPresent());
    }
}