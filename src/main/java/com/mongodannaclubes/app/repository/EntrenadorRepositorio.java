package com.mongodannaclubes.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mongodannaclubes.app.variables.Entrenador;

public interface EntrenadorRepositorio extends MongoRepository <Entrenador, String>{

}
