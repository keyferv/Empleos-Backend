package com.example.empleos.persistence.repository;

import com.example.empleos.persistence.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Integer> {
    Usuarios findByUsername(String username);
    Optional<Usuarios> findUsuariosByUsername(String username);

    List<Usuarios> findByRolesId(Integer roleId);
}
