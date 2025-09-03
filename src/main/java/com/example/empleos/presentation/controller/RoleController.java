package com.example.empleos.presentation.controller;

import com.example.empleos.service.interfaces.RoleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleServices roleService;

    // Usar el servicio de roles para eliminar un rol
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable("id") Integer id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok("Rol eliminado correctamente");
    }

}
