package com.example.empleos.presentation.dto.request;

import com.example.empleos.persistence.entity.Estado;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRequestDTO {

    private String name;
    private String lastname;
    private String email;
    private String password;
    private String username;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date birthDate;

    private String jobTitle;
    private String phone;
    private String certifications;

    private Integer estadoId; // Solo el ID del estado
    private Estado estado;

    private Set<Integer> roleIds; // Lista de IDs de roles
    private Set<String> roles; // Lista de nombres de roles

    private boolean isEnabled;
    private boolean accountNoExpired;
    private boolean accountNoLocked;
    private boolean credentialNoExpired;
}
