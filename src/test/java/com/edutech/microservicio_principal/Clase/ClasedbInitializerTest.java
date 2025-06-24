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
        // Simula los repositorios necesarios
        ClaseRepository mockClaseRepo = mock(ClaseRepository.class);
        CursoRepository mockCursoRepo = mock(CursoRepository.class);

        // Configura el repositorio de clases para que actúe como si no hubiera datos
        when(mockClaseRepo.count()).thenReturn(0L);

        // Crea cursos de prueba con nombres que coinciden con las condiciones del initializer
        Curso cursoJava = new Curso();
        cursoJava.setNombre("Curso de Java");

        Curso cursoIngles = new Curso();
        cursoIngles.setNombre("Curso de Inglés Básico");

        Curso cursoJapones = new Curso();
        cursoJapones.setNombre("Curso de Japonés");

        // El repositorio de cursos devuelve la lista simulada
        when(mockCursoRepo.findAll()).thenReturn(List.of(cursoJava, cursoIngles, cursoJapones));

        // Ejecuta el método run() del initializer
        ClasedbInitializer initializer = new ClasedbInitializer(mockClaseRepo, mockCursoRepo);
        initializer.run();

        // Verifica que se haya llamado saveAll() tres veces, una por cada curso encontrado
        verify(mockClaseRepo, times(3)).saveAll(any());
    }

    @Test
    void testNoHaceNadaSiYaHayDatos() {
        // Se crean mocks de los repositorios
        ClaseRepository mockClaseRepo = mock(ClaseRepository.class);
        CursoRepository mockCursoRepo = mock(CursoRepository.class);

        // Simula que ya existen 10 clases en la base de datos
        when(mockClaseRepo.count()).thenReturn(10L);

        // Ejecuta el initializer
        ClasedbInitializer initializer = new ClasedbInitializer(mockClaseRepo, mockCursoRepo);
        initializer.run();

        // Verifica que no se llamó a findAll ni a saveAll porque ya había datos
        verify(mockCursoRepo, never()).findAll();
        verify(mockClaseRepo, never()).saveAll(any());
    }
}