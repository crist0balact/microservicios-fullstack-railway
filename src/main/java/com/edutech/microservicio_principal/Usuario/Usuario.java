package com.edutech.microservicio_principal.Usuario;

import com.edutech.microservicio_principal.Curso.Curso;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 30)
    @Column(nullable = false)
    private String nombres;

    @NotNull
    @Size(min = 3, max = 30)
    @Column(nullable = false)
    private String apellidos;

    @NotNull
    @Email
    @Column(unique = true, nullable = false)
    private String correo;

    @NotNull
    @Size(min = 7)
    @Column(nullable = false)
    private String contraseña;

    @NotNull
    @Size(min = 5, max = 50)
    @Column(nullable = false)
    private String rol;

    // Lista de cursos en los que el usuario está inscrito
    @ManyToMany
    @JoinTable(
        name = "usuarios_cursos_inscritos",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
private List<Curso> cursosInscritos = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(Long id, String nombres, String apellidos, String correo, String contraseña, String rol) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public List<Curso> getCursosInscritos() {
        return cursosInscritos;
    }

    public void setCursosInscritos(List<Curso> cursosInscritos) {
        this.cursosInscritos = cursosInscritos;
    }

    @Override
    public String toString() {
        return "Usuario{" +
               "id=" + id +
               ", nombres='" + nombres + '\'' +
               ", apellidos='" + apellidos + '\'' +
               ", correo='" + correo + '\'' +
               ", contraseña='" + "********" + '\'' +
               ", rol='" + rol + '\'' +
               ", cursosInscritos=" + cursosInscritos.size() +
               '}';
    }
}