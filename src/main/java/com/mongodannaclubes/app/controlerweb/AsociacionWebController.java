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
import com.mongodannaclubes.app.repository.AsociacionRepositorio;
import com.mongodannaclubes.app.variables.Asociacion;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "asociaciones")

public class AsociacionWebController {
	@Autowired
	private AsociacionRepositorio asociacionRepository;

	@GetMapping("/")
	public String asociacionesListTemplate(Model model) {
		model.addAttribute("asociaciones", asociacionRepository.findAll());
		return "asociaciones-list";
	}

    @GetMapping("/new")
    public String asociacionesNewTemplate(Model model) {
        model.addAttribute("asociacion", new Asociacion());
        return "asociaciones-form";
    }
	@GetMapping("/edit/{id}")
	public String asociacionEditTemplate(@PathVariable("id") String id, Model model) {
		model.addAttribute("asociacion",
				asociacionRepository.findById(id).orElseThrow(() -> new NotFoundException("Asociacion no encontrado")));
		return "asociaciones-form";
	}

	@PostMapping("/save")
	public String asociacionesSaveProcess(@Valid @ModelAttribute("asociacion") Asociacion asociacion, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			return "asociaciones-form"; // Retorna al formulario si hay errores
		}
		if (asociacion.getId() == null || asociacion.getId().isEmpty()) {
			asociacion.setId(null); // MongoDB generará un ID automáticamente
		}
		asociacionRepository.save(asociacion);
		return "redirect:/asociaciones/";
	}

	@GetMapping("/delete/{id}")
	public String asociacionDeleteProcess(@PathVariable("id") String id, Model model) {
	    try {
	        asociacionRepository.deleteById(id);
	        return "redirect:/asociaciones/";
	    } catch (Exception e) {
	        model.addAttribute("errorMessage", "Error al eliminar la asociacion: " + e.getMessage());
	        model.addAttribute("asociaciones", asociacionRepository.findAll());
	        return "asociaciones-list"; // Asegúrate de manejar la excepción y mostrar un mensaje de error
	    }
	}

	@GetMapping("/test")
	public String testTemplate(Model model) {
		return "test"; // Asegúrate de crear src/main/resources/templates/test.html
	}

}