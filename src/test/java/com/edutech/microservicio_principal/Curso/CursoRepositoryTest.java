package com.edutech.microservicio_principal.Curso;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CursoRepositoryTest {

    @Autowired
    private CursoRepository cursoRepository;

    @Test
    void testGuardarYBuscarCurso() {
        Curso curso = new Curso();
        curso.setNombre("Curso de Kotlin");
        curso.setDescripcion("Programación moderna");
        curso.setValorCurso(22000);

        cursoRepository.save(curso);

        List<Curso> todos = cursoRepository.findAll();
        assertEquals(1, todos.size());
        assertEquals("Curso de Kotlin", todos.get(0).getNombre());
    }

    @Test
    void testFindByNombre() {
        Curso curso = new Curso();
        curso.setNombre("Curso de Spring Boot");
        curso.setDescripcion("Microservicios en Java");
        curso.setValorCurso(18000);

        cursoRepository.save(curso);

        Optional<Curso> encontrado = cursoRepository.findByNombre("Curso de Spring Boot");
        assertTrue(encontrado.isPresent());
        assertEquals("Microservicios en Java", encontrado.get().getDescripcion());
    }

    @Test
    void testFindByNombreNoExiste() {
        Optional<Curso> resultado = cursoRepository.findByNombre("No existe");
        assertFalse(resultado.isPresent());
    }
}