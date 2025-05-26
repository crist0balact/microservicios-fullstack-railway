package com.edutech.microservicio_principal.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public Optional<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id);
    }

    @PostMapping
    public Usuario guardarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.guardarUsuario(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        return usuarioService.obtenerUsuarioPorId(id)
                .map(usuarioExistente -> {
                    usuarioExistente.setNombres(usuarioActualizado.getNombres());
                    usuarioExistente.setApellidos(usuarioActualizado.getApellidos());
                    usuarioExistente.setCorreo(usuarioActualizado.getCorreo());
                    usuarioExistente.setContraseña(usuarioActualizado.getContraseña());
                    usuarioExistente.setRol(usuarioActualizado.getRol());

                    Usuario usuarioGuardado = usuarioService.guardarUsuario(usuarioExistente);
                    return ResponseEntity.ok(usuarioGuardado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }
}