package com.mongodannaclubes.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mongodannaclubes.app.variables.Club;

public interface ClubRepositorio extends MongoRepository<Club, String>{

}
