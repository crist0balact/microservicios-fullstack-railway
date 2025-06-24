package com.edutech.microservicio_principal.Curso;

import org.junit.jupiter.api.Test;

/*import java.util.List;*/

import static org.mockito.Mockito.*;

class CursodbInitializerTest {

    @Test
    void testCargaCursosSiRepoVacio() {
        // Simular repositorio
        CursoRepository cursoRepository = mock(CursoRepository.class);

        // Simula base de datos vacía
        when(cursoRepository.count()).thenReturn(0L);

        // Ejecutar initializer
        CursodbInitializer initializer = new CursodbInitializer(cursoRepository);
        initializer.run();

        // Verificar que saveAll se llamó una vez con una lista de cursos
        verify(cursoRepository, times(1)).saveAll(any());
    }

    @Test
    void testNoHaceNadaSiYaHayCursos() {
        CursoRepository cursoRepository = mock(CursoRepository.class);

        // Simula que ya hay cursos
        when(cursoRepository.count()).thenReturn(3L);

        // Ejecutar initializer
        CursodbInitializer initializer = new CursodbInitializer(cursoRepository);
        initializer.run();

        // Verifica que NO se intentó guardar nada
        verify(cursoRepository, never()).saveAll(any());
    }
}
