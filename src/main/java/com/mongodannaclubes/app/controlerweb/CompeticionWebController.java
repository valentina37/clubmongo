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
import com.mongodannaclubes.app.repository.CompeticionRepositorio;
import com.mongodannaclubes.app.variables.Competicion;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "competiciones")

public class CompeticionWebController {
	@Autowired
	private CompeticionRepositorio competicionRepository;

	@GetMapping("/")
	public String competicionesListTemplate(Model model) {
		model.addAttribute("competiciones", competicionRepository.findAll());
		return "competiciones-list";
	}

    @GetMapping("/new")
    public String competicionesNewTemplate(Model model) {
        model.addAttribute("competicion", new Competicion());
        return "competiciones-form";
    }
	@GetMapping("/edit/{id}")
	public String competicionEditTemplate(@PathVariable("id") String id, Model model) {
		model.addAttribute("competicion",
				competicionRepository.findById(id).orElseThrow(() -> new NotFoundException("Competicion no encontrado")));
		return "competiciones-form";
	}

	@PostMapping("/save")
	public String competicionesSaveProcess(@Valid @ModelAttribute("competicion") Competicion competicion, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			return "competiciones-form"; // Retorna al formulario si hay errores
		}
		if (competicion.getId() == null || competicion.getId().isEmpty()) {
			competicion.setId(null); // MongoDB generará un ID automáticamente
		}
		competicionRepository.save(competicion);
		return "redirect:/competiciones/";
	}

	@GetMapping("/delete/{id}")
	public String competicionDeleteProcess(@PathVariable("id") String id) {
		competicionRepository.deleteById(id);
		return "redirect:/competiciones/";
	}

	@GetMapping("/test")
	public String testTemplate(Model model) {
		return "test"; // Asegúrate de crear src/main/resources/templates/test.html
	}

}