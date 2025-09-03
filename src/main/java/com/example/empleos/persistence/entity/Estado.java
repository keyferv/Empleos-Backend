package com.example.empleos.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @Enumerated(EnumType.STRING) // Usa el nombre del ENUM como valor
    @Column(nullable = false, unique = true)
    private EstadoType type;

    public Estado(EstadoType type) {
        this.type = type;
    }
}
