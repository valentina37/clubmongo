package com.mongodannaclubes.app.controlerweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mongodannaclubes.app.NotFoundException.NotFoundException;
import com.mongodannaclubes.app.repository.EntrenadorRepositorio;
import com.mongodannaclubes.app.variables.Entrenador;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "entrenadores")

public class EntrenadorWebController {
	@Autowired
	private EntrenadorRepositorio entrenadorRepository;

	@GetMapping("/")
	public String entrenadoresListTemplate(Model model) {
		model.addAttribute("entrenadores", entrenadorRepository.findAll());
		return "entrenadores-list";
	}

    @GetMapping("/new")
    public String entrenadoresNewTemplate(Model model) {
        model.addAttribute("entrenador", new Entrenador());
        return "entrenadores-form";
    }
	@GetMapping("/edit/{id}")
	public String entrenadorEditTemplate(@PathVariable("id") String id, Model model) {
		model.addAttribute("entrenador",
				entrenadorRepository.findById(id).orElseThrow(() -> new NotFoundException("Entrenador no encontrado")));
		return "entrenadores-form";
	}

	@PostMapping("/save")
	public String entrenadoresSaveProcess(@Valid @ModelAttribute("entrenador") Entrenador entrenador, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			return "entrenadores-form"; // Retorna al formulario si hay errores
		}
		if (entrenador.getId() == null || entrenador.getId().isEmpty()) {
			entrenador.setId(null); // MongoDB generará un ID automáticamente
		}
		entrenadorRepository.save(entrenador);
		return "redirect:/entrenadores/";
	}
	@GetMapping("/delete/{id}")
	public String entrenadorDeleteProcess(@PathVariable("id") String id, Model model) {
	    try {
	        entrenadorRepository.deleteById(id);
	        return "redirect:/entrenadores/";
	    } catch (Exception e) {
	        model.addAttribute("errorMessage", "Error al eliminar el entrenador: " + e.getMessage());
	        model.addAttribute("entrenadores", entrenadorRepository.findAll());
	        return "entrenadores-list"; // Asegúrate de manejar la excepción y mostrar un mensaje de error
	    }
	}

	@GetMapping("/test")
	public String testTemplate(Model model) {
		return "test"; // Asegúrate de crear src/main/resources/templates/test.html
	}

}