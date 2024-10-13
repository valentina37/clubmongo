package com.mongodannaclubes.app.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.mongodannaclubes.app.variables.Jugador;

public interface JugadorRepositorio extends MongoRepository <Jugador, String>{

}
