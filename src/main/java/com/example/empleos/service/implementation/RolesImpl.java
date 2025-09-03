package com.example.empleos.service.implementation;

import com.example.empleos.persistence.entity.Roles;
import com.example.empleos.persistence.repository.RoleRepository;
import com.example.empleos.service.interfaces.RoleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;

@Service
@Primary
public class RolesImpl implements RoleServices {

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public String deleteRole(Integer id) {
        Roles roles = roleRepository.findById(id)
                .orElseThrow(() -> new ResolutionException("El rol con el ID " + id + " no existe"));
        roleRepository.delete(roles);
        return "El rol con el ID " + id + " ha sido eliminado";
    }
}
