package com.edutech.microservicio_principal.Curso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    // Método para buscar un curso por su nombre
    Optional<Curso> findByNombre(String nombre);
}