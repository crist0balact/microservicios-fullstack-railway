package com.edutech.microservicio_principal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")  // Usa el perfil "test"
public class EdutechLearningPlatformBackendApplicationTests {

    @Test
    public void contextLoads() {
        // Verifica que el contexto de Spring se carga
    }
}