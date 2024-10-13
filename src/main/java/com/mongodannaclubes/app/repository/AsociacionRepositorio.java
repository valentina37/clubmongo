package com.mongodannaclubes.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mongodannaclubes.app.variables.Asociacion;

public interface AsociacionRepositorio extends MongoRepository<Asociacion, String> {

}
