package com.mongodannaclubes.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mongodannaclubes.app.variables.Competicion;

public interface CompeticionRepositorio extends MongoRepository<Competicion, String>{

}