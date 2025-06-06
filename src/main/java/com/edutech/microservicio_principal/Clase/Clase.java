package com.edutech.microservicio_principal.Clase;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "clases")
public class Clase {

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
    private int cuposDisponibles; // Número de cupos

    @NotNull
    @Column(nullable = false)
    private String horario; // Horario en formato String 

    public Clase() {
    }

    public Clase(Long id, String nombre, String descripcion, int cuposDisponibles, String horario) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cuposDisponibles = cuposDisponibles;
        this.horario = horario;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getCuposDisponibles() { return cuposDisponibles; }

    public void setCuposDisponibles(int cuposDisponibles) { this.cuposDisponibles = cuposDisponibles; }

    public String getHorario() { return horario; }

    public void setHorario(String horario) { this.horario = horario; }

    @Override
    public String toString() {
        return "Clase{" +
               "id=" + id +
               ", nombre='" + nombre + '\'' +
               ", descripcion='" + descripcion + '\'' +
               ", cuposDisponibles=" + cuposDisponibles +
               ", horario='" + horario + '\'' +
               '}';
    }
}
