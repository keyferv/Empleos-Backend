package com.example.empleos.persistence.repository;

import com.example.empleos.persistence.entity.Estado;
import com.example.empleos.persistence.entity.EstadoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

    Estado save(Estado estado);

    void deleteById(Integer id);

    Estado getById(Integer id);

    // estado por id
    Optional<Estado> findById(Integer id);

    Optional<Estado> findByType(EstadoType type);
}
