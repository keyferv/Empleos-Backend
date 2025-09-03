package com.example.empleos.persistence.repository;

import com.example.empleos.persistence.entity.Roles;
import com.example.empleos.persistence.entity.RolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {

    Optional<Roles> findById(Integer id);

    Optional<Roles> findByRolesEnum(RolesEnum rolesEnum);

}
