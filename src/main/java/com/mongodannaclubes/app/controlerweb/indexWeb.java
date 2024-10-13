package com.mongodannaclubes.app.controlerweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexWeb {

    @GetMapping("/index")
    public String index() {
        return "index";  // Esto buscará el archivo index.html en /src/main/resources/templates/
    }
}
