package com.pelicula.pelicula.controllers;

import com.pelicula.pelicula.model.Pelicula;
import com.pelicula.pelicula.service.PeliculaService;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/peliculas")
public class PeliculaController {

    private final PeliculaService peliculaService;
    private final Tracer tracer;

    @Autowired
    public PeliculaController(PeliculaService peliculaService, Tracer tracer) {
        this.peliculaService = peliculaService;
        this.tracer = tracer;
    }

    @GetMapping
    public ResponseEntity<List<Pelicula>> getAllPeliculas() {
        var span = tracer.buildSpan("getAllPeliculas").start();
        try {
            List<Pelicula> peliculas = peliculaService.findAllPeliculas();
            return ResponseEntity.ok(peliculas);
        } catch (Exception e) {
            // Registro de la excepción
            return ResponseEntity.status(500).body(null);
        } finally {
            span.finish();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pelicula> getPeliculaById(@PathVariable Long id) {
        var span = tracer.buildSpan("getPeliculaById").start();
        try {
            Optional<Pelicula> pelicula = peliculaService.findPeliculaById(id);
            return pelicula.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            // Registro de la excepción
            return ResponseEntity.status(500).body(null);
        } finally {
            span.finish();
        }
    }

    @PostMapping
    public ResponseEntity<Pelicula> createPelicula(@RequestBody Pelicula pelicula) {
        var span = tracer.buildSpan("createPelicula").start();
        try {
            Pelicula nuevaPelicula = peliculaService.savePelicula(pelicula);
            return ResponseEntity.status(201).body(nuevaPelicula);
        } catch (Exception e) {
            // Registro de la excepción
            return ResponseEntity.status(500).body(null);
        } finally {
            span.finish();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pelicula> updatePelicula(@PathVariable Long id, @RequestBody Pelicula pelicula) {
        var span = tracer.buildSpan("updatePelicula").start();
        try {
            return peliculaService.findPeliculaById(id)
                    .map(peliculaExistente -> {
                        pelicula.setId(id);
                        return ResponseEntity.ok(peliculaService.savePelicula(pelicula));
                    })
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            // Registro de la excepción
            return ResponseEntity.status(500).body(null);
        } finally {
            span.finish();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePelicula(@PathVariable Long id) {
        var span = tracer.buildSpan("deletePelicula").start();
        try {
            if (peliculaService.findPeliculaById(id).isPresent()) {
                peliculaService.deletePelicula(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Registro de la excepción
            return ResponseEntity.status(500).body(null);
        } finally {
            span.finish();
        }
    }

    // Otros métodos del controlador si son necesarios
}