package com.mongodannaclubes.app.controlerweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.mongodannaclubes.app.NotFoundException.NotFoundException;
import com.mongodannaclubes.app.repository.ClubRepositorio;
import com.mongodannaclubes.app.repository.JugadorRepositorio;
import com.mongodannaclubes.app.variables.Jugador;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "/jugadores") // Asegúrate de que la ruta comience con "/"
public class JugadorWebController {
    
    @Autowired
    private JugadorRepositorio jugadorRepository;
    
    @Autowired
    private ClubRepositorio clubRepository; // Inyección del ClubRepositorio

    @GetMapping("/")
    public String jugadoresListTemplate(Model model) {
        model.addAttribute("jugadores", jugadorRepository.findAll());
        return "jugadores-list"; // Asegúrate de que este template existe
    }

    @GetMapping("/new")
    public String jugadoresNewTemplate(Model model) {
        model.addAttribute("jugador", new Jugador());
        model.addAttribute("listaClubes", clubRepository.findAll()); // Añadir lista de clubes
        return "jugadores-form"; // Asegúrate de que este template existe
    }


    @GetMapping("/edit/{id}")
    public String jugadorEditTemplate(@PathVariable("id") String id, Model model) {
        Jugador jugador = jugadorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Jugador no encontrado"));
        model.addAttribute("jugador", jugador);
        model.addAttribute("listaClubes", clubRepository.findAll()); // Añadir lista de clubes
        return "jugadores-form";
    }

    @PostMapping("/save")
    public String jugadoresSaveProcess(@Valid @ModelAttribute("jugador") Jugador jugador, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listaClubes", clubRepository.findAll()); // Reagregar lista de clubes si hay errores
            return "jugadores-form"; // Retorna al formulario si hay errores
        }
        if (jugador.getId() == null || jugador.getId().isEmpty()) {
            jugador.setId(null); // MongoDB generará un ID automáticamente
        }
        jugadorRepository.save(jugador);
        return "redirect:/jugadores/";
    }

    @GetMapping("/delete/{id}")
    public String jugadorDeleteProcess(@PathVariable("id") String id) {
        jugadorRepository.deleteById(id);
        return "redirect:/jugadores/";
    }

    @GetMapping("/test")
    public String testTemplate(Model model) {
        return "test"; // Asegúrate de crear src/main/resources/templates/test.html
    }
}
