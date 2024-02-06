package com.pelicula.pelicula.service;

import com.pelicula.pelicula.model.Pelicula;
import com.pelicula.pelicula.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculaService {

    private final PeliculaRepository peliculaRepository;

    @Autowired
    public PeliculaService(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    public List<Pelicula> findAllPeliculas() {
        return peliculaRepository.findAll();
    }

    public Optional<Pelicula> findPeliculaById(Long id) {
        return peliculaRepository.findById(id);
    }

    public Pelicula savePelicula(Pelicula pelicula) {
        return peliculaRepository.save(pelicula);
    }

    public void deletePelicula(Long id) {
        peliculaRepository.deleteById(id);
    }
}