package com.edutech.microservicio_principal;

import jakarta.persistence.*;

@Entity
public class Curso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private int valorCurso;

    public Curso() {
    }

    public Curso(Long id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
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
               ", valor='" + valorCurso + '\'' +
               '}';
    }
}