package com.example.empleos.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_name")
    private String categoryName;
    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "categories")
    @Builder.Default
    private Set<Vacantes> vacancies = new HashSet<>();
}
