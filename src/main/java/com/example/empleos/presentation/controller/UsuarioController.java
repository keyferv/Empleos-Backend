package com.example.empleos.presentation.controller;

import com.example.empleos.persistence.entity.Usuarios;
import com.example.empleos.presentation.dto.request.UsuarioRequestDTO;
import com.example.empleos.presentation.dto.response.UsuarioResponseDTO;
import com.example.empleos.service.interfaces.UsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioServices usuarioServices;

    // Crear un usuario
    @PostMapping("/add")
    public ResponseEntity<UsuarioResponseDTO> addUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioResponseDTO usuarioResponseDTO = usuarioServices.addUsuario(usuarioRequestDTO);
        return new ResponseEntity<>(usuarioResponseDTO, HttpStatus.CREATED);
    }

    // Obtener todos los usuarios
    @GetMapping("/findAll")
    public ResponseEntity<List<UsuarioResponseDTO>> findAll() {
        return new ResponseEntity<>(usuarioServices.getAllUsuarios(), HttpStatus.OK);
    }

    //Elimnar un usuario por su idUsuario
    @DeleteMapping("/delete/{idUsuario}")
    public ResponseEntity<String> deleteUsuario(@PathVariable("idUsuario") Integer idUsuario) {
        usuarioServices.deleteUsuario(idUsuario);
        return ResponseEntity.ok("Usuario eliminado con éxito");
    }

    // Actualizar un usuario por su idUsuario
    @PutMapping("/update/{idUsuario}")
    public ResponseEntity<UsuarioResponseDTO> updateUsuario(@PathVariable("idUsuario") Integer idUsuario, @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioResponseDTO usuarioResponseDTO = usuarioServices.updateUsuario(idUsuario, usuarioRequestDTO);
        return new ResponseEntity<>(usuarioResponseDTO, HttpStatus.OK);
    }

    // Traer un usuario por su idUsuario
    @GetMapping("/findById/{idUsuario}")
    public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable("idUsuario") Integer idUsuario) {
        UsuarioResponseDTO usuarioResponseDTO = usuarioServices.getUsuarioById(idUsuario);
        return new ResponseEntity<>(usuarioResponseDTO, HttpStatus.OK);
    }

    //Trarr un usuario por su roleID
    @GetMapping("/findByRole/{roleId}")
    public ResponseEntity<List<UsuarioResponseDTO>> findByRole(@PathVariable("roleId") Integer roleId) {
        return new ResponseEntity<>(usuarioServices.getUsuariosByRole(roleId), HttpStatus.OK);
    }



}
