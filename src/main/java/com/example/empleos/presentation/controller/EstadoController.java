package com.example.empleos.presentation.controller;

import com.example.empleos.persistence.entity.Estado;
import com.example.empleos.persistence.repository.EstadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estados")
@AllArgsConstructor
public class EstadoController {

    private final EstadoRepository estadoRepository;

    @GetMapping
    public ResponseEntity<List<Estado>> getAllEstados() {
        List<Estado> estados = estadoRepository.findAll();
        return new ResponseEntity<>(estados, HttpStatus.OK);
    }
}