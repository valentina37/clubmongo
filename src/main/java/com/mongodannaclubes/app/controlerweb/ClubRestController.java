package com.mongodannaclubes.app.controlerweb;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mongodannaclubes.app.NotFoundException.NotFoundException;
import com.mongodannaclubes.app.repository.ClubRepositorio;
import com.mongodannaclubes.app.variables.Club;
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
@RequestMapping(value = "/api/clubes")
public class ClubRestController {
	
	 @Autowired
	    private ClubRepositorio clubRepository;
	 
	 @GetMapping("/")
	    public List<Club> getAllClubs() {
	        return clubRepository.findAll();
	    }

	    @GetMapping("/{id}")
	    public Club getClubById(@PathVariable String id) {
	        return clubRepository.findById(id).orElseThrow(() -> new NotFoundException("Club no encontrado"));
	    }

	    @PostMapping("/")
	    public Club saveClub(@RequestBody Map<String, Object> body) {
	        ObjectMapper mapper = new ObjectMapper();
	        Club club = mapper.convertValue(body, Club.class);
	        return clubRepository.save(club);
	    }

	    @PutMapping("/{id}")
	    public Club updateClub(@PathVariable String id, @RequestBody Map<String, Object> body) {
	        ObjectMapper mapper = new ObjectMapper();
	        Club club = mapper.convertValue(body, Club.class);
	        club.setId(id);
	        return clubRepository.save(club);
	    }

	    @DeleteMapping("/{id}")
	    public Club deleteClub(@PathVariable String id) {
	        Club club = clubRepository.findById(id).orElseThrow(() -> new NotFoundException("Club no encontrado"));
	        clubRepository.deleteById(id);
	        return club;
	    }	 

}
