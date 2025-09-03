package com.example.empleos.persistence.entity;

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
public class Solicitudes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "request_date")
    private Date requestDate;
    private String file;
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vacante_id")
    private Vacantes vacancy;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "usuario_id")
//    private Usuarios users;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estado;

}
