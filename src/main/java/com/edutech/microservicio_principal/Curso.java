package com.edutech.microservicio_principal;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

@Entity
public class Curso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 100)
    @Column(nullable = false)
    private String nombre;

    @NotNull
    @Size(max = 255)
    @Column(nullable = false)
    private String descripcion;

    @Min(0)
    @Column(nullable = false)
    private int valorCurso;

    public Curso() {
    }

    public Curso(Long id, String nombre, String descripcion, int valorCurso) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.valorCurso = valorCurso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getValorCurso() {
        return valorCurso;
    }

    public void setValorCurso(int valorCurso) {
        this.valorCurso = valorCurso;
    }

    @Override
    public String toString() {
        return "Curso{" +
               "id=" + id +
               ", nombre='" + nombre + '\'' +
               ", descripcion='" + descripcion + '\'' +
               ", valor=" + valorCurso +
               '}';
    }
}