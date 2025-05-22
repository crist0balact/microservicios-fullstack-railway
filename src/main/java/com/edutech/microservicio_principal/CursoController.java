package com.edutech.microservicio_principal;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.persistence.*;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @GetMapping
    public List<Curso> getAllCursos() {
    }

    @PostMapping
    public Curso createCurso(@RequestBody Curso curso) {
    }

    @GetMapping("/{id}")
    public Curso getCursoById(@PathVariable Long id) {
    }
}