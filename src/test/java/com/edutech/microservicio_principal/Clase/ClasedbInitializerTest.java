package com.edutech.microservicio_principal.Clase;

import com.edutech.microservicio_principal.Curso.Curso;
import com.edutech.microservicio_principal.Curso.CursoRepository;
import org.junit.jupiter.api.Test;
/*import org.mockito.Mockito;*/

import java.util.List;

/*import static org.junit.jupiter.api.Assertions.assertTrue;*/
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClasedbInitializerTest {

    @Test
    void testCargaClasesCuandoNoHayDatos() {
        // Simular repositorios
        ClaseRepository mockClaseRepo = mock(ClaseRepository.class);
        CursoRepository mockCursoRepo = mock(CursoRepository.class);

        // Simula que no hay clases aún
        when(mockClaseRepo.count()).thenReturn(0L);

        // Simula cursos con nombres esperados por el switch
        Curso cursoJava = new Curso();
        cursoJava.setNombre("Curso de Java");

        Curso cursoIngles = new Curso();
        cursoIngles.setNombre("Curso de Inglés Básico");

        Curso cursoJapones = new Curso();
        cursoJapones.setNombre("Curso de Japonés");

        when(mockCursoRepo.findAll()).thenReturn(List.of(cursoJava, cursoIngles, cursoJapones));

        // Ejecutar el initializer
        ClasedbInitializer initializer = new ClasedbInitializer(mockClaseRepo, mockCursoRepo);
        initializer.run();

        // Verifica que saveAll() fue invocado tres veces (una por cada curso)
        verify(mockClaseRepo, times(3)).saveAll(any());
    }

    @Test
    void testNoHaceNadaSiYaHayDatos() {
        // Repositorios simulados
        ClaseRepository mockClaseRepo = mock(ClaseRepository.class);
        CursoRepository mockCursoRepo = mock(CursoRepository.class);

        // Simula que ya hay registros
        when(mockClaseRepo.count()).thenReturn(10L);

        // Ejecutar el initializer
        ClasedbInitializer initializer = new ClasedbInitializer(mockClaseRepo, mockCursoRepo);
        initializer.run();

        // Verifica que no se llamaron métodos porque ya había datos
        verify(mockCursoRepo, never()).findAll();
        verify(mockClaseRepo, never()).saveAll(any());
    }
}
