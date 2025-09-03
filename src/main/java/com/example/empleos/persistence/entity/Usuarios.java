package com.example.empleos.persistence.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    private String name;
    private String lastname;
    private String email;
    private String password;
    private String username;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegister;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "job_title")
    private String jobTitle;
    private String phone;
    private String certifications;

    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled = true; // ✅ Por defecto: activo

    @Column(name = "account_No_Expired", nullable = false)
    private boolean accountNoExpired = true;

    @Column(name = "account_No_Locked", nullable = false)
    private boolean accountNoLocked = true;

    @Column(name = "credential_No_Expired", nullable = false)
    private boolean credentialNoExpired = true;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> roles = new HashSet<>();

//    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Solicitudes> requests = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estado;

    // 🟢 Al crear un usuario, se establece el estado y la fecha automáticamente
    @PrePersist
    public void prePersist() {
        this.dateRegister = new Date(); // Fecha actual al guardar
        if (estado == null) {
            this.estado = new Estado(EstadoType.ACTIVO); // Estado por defecto
        }
    }

}
