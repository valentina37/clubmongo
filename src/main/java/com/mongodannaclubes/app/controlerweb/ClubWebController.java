package com.mongodannaclubes.app.controlerweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.mongodannaclubes.app.NotFoundException.NotFoundException;
import com.mongodannaclubes.app.repository.AsociacionRepositorio;
import com.mongodannaclubes.app.repository.ClubRepositorio;
import com.mongodannaclubes.app.repository.CompeticionRepositorio;
import com.mongodannaclubes.app.repository.EntrenadorRepositorio;
import com.mongodannaclubes.app.variables.Club;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "/clubes") // Corregido de "clubles" a "/clubes"
public class ClubWebController {
    @Autowired
    private ClubRepositorio clubRepository;

    @Autowired
    private EntrenadorRepositorio entrenadorRepository; // Repositorio para Entrenadores

    @Autowired
    private AsociacionRepositorio asociacionRepository; // Repositorio para Asociaciones

    @Autowired
    private CompeticionRepositorio competicionRepository; // Repositorio para Competiciones

    // Método para listar todos los clubes
    @GetMapping("/")
    public String clubesListTemplate(Model model) {
        model.addAttribute("clubes", clubRepository.findAll()); // Asegúrate de que el atributo es "clubes"
        return "clubes-list"; // Asegúrate de que este es el nombre correcto de tu plantilla
    }

    // Método para mostrar el formulario de creación de un nuevo club
    @GetMapping("/new")
    public String clubesNewTemplate(Model model) {
        model.addAttribute("club", new Club());

        // Agregar listas necesarias para los selects en el formulario
        model.addAttribute("listaEntrenador", entrenadorRepository.findAll());
        model.addAttribute("listaAsociacion", asociacionRepository.findAll());
        model.addAttribute("listaCompeticion", competicionRepository.findAll());

        return "clubes-form"; // Asegúrate de que este es el nombre correcto de tu plantilla
    }

    // Método para mostrar el formulario de edición de un club existente
    @GetMapping("/edit/{id}")
    public String clubEditTemplate(@PathVariable("id") String id, Model model) {
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Club no encontrado"));
        model.addAttribute("club", club);

        // Agregar listas necesarias para los selects en el formulario
        model.addAttribute("listaEntrenador", entrenadorRepository.findAll());
        model.addAttribute("listaAsociacion", asociacionRepository.findAll());
        model.addAttribute("listaCompeticion", competicionRepository.findAll());

        return "clubes-form"; // Asegúrate de que este es el nombre correcto de tu plantilla
    }


	@PostMapping("/save")
	public String clubesSaveProcess(@Valid @ModelAttribute("club") Club club, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("listaEntrenador", entrenadorRepository.findAll());
			model.addAttribute("listaAsociacion", asociacionRepository.findAll());
			model.addAttribute("listaCompeticion", competicionRepository.findAll());
			return "clubes-form";
		}
	
		// Si el id es nulo, MongoDB generará uno automáticamente
		if (club.getId() == null || club.getId().isEmpty()) {
			club.setId(null); // Asegura que se genere el ID de forma automática
		}
	
		clubRepository.save(club);
		return "redirect:/clubes/";
	}
	

    // Método para eliminar un club
	@GetMapping("/delete/{id}")
	public String clubDeleteProcess(@PathVariable("id") String id, Model model) {
	    try {
	        clubRepository.deleteById(id);
	        return "redirect:/clubes/";
	    } catch (Exception e) {
	        model.addAttribute("errorMessage", "Error al eliminar el club: " + e.getMessage());
	        model.addAttribute("clubes", clubRepository.findAll());
	        return "clubes-list"; // Asegúrate de manejar la excepción y mostrar un mensaje de error
	    }
	}


    // Método de prueba para verificar el funcionamiento
    @GetMapping("/test")
    public String testTemplate(Model model) {
        return "test"; // Asegúrate de crear src/main/resources/templates/test.html
    }
}
