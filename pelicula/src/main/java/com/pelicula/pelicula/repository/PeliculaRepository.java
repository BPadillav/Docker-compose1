package com.pelicula.pelicula.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.pelicula.pelicula.model.Pelicula;

public interface PeliculaRepository extends MongoRepository<Pelicula, Long> {
    Pelicula findByTitulo(String titulo);
}

