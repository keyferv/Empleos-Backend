package com.example.empleos.presentation.controller;

import com.example.empleos.persistence.entity.Categoria;
import com.example.empleos.persistence.repository.CategoriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@AllArgsConstructor
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<Categoria>> getAllCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }
}