package com.example.empleos.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Solicitudes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "request_date", nullable = false)
    private Date requestDate;
    private String file;
    private String comment;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacante_id")
    private Vacantes vacancy;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuarios usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estado;

}
