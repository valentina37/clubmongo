package com.mongodannaclubes.app.controlerweb;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mongodannaclubes.app.NotFoundException.NotFoundException;
import com.mongodannaclubes.app.repository.EntrenadorRepositorio;
import com.mongodannaclubes.app.variables.Entrenador;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/entrenadores")
public class EntrenadorRestController {
	
	 @Autowired
	    private EntrenadorRepositorio entrenadorRepository;
	 
	 @GetMapping("/")
	    public List<Entrenador> getAllEntrenadors() {
	        return entrenadorRepository.findAll();
	    }

	    @GetMapping("/{id}")
	    public Entrenador getEntrenadorById(@PathVariable String id) {
	        return entrenadorRepository.findById(id).orElseThrow(() -> new NotFoundException("Entrenador no encontrado"));
	    }

	    @PostMapping("/")
	    public Entrenador saveEntrenador(@RequestBody Map<String, Object> body) {
	        ObjectMapper mapper = new ObjectMapper();
	        Entrenador entrenador = mapper.convertValue(body, Entrenador.class);
	        return entrenadorRepository.save(entrenador);
	    }

	    @PutMapping("/{id}")
	    public Entrenador updateEntrenador(@PathVariable String id, @RequestBody Map<String, Object> body) {
	        ObjectMapper mapper = new ObjectMapper();
	        Entrenador entrenador = mapper.convertValue(body, Entrenador.class);
	        entrenador.setId(id);
	        return entrenadorRepository.save(entrenador);
	    }

	    @DeleteMapping("/{id}")
	    public Entrenador deleteEntrenador(@PathVariable String id) {
	        Entrenador entrenador = entrenadorRepository.findById(id).orElseThrow(() -> new NotFoundException("Entrenador no encontrado"));
	        entrenadorRepository.deleteById(id);
	        return entrenador;
	    }	 

}
