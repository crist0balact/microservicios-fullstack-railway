package com.edutech.microservicio_principal.Vista;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VistaMenuController {

    @GetMapping("/menu")
    public String mostrarMenu() {
        return "menu"; // Busca a menu.html en templates
    }
}