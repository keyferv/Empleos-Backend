package com.example.empleos.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponseDTO {

    private Integer idUsuario;
    private String name;
    private String lastname;
    private String email;
    private String username;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date birthDate;

    private String jobTitle;
    private String phone;
    private String certifications;

    private boolean isEnabled;
    private boolean accountNoExpired;
    private boolean accountNoLocked;
    private boolean credentialNoExpired;

    private String estado; // Nombre del estado

    private Set<String> roles; // Nombres de los roles

    private Date dateRegister; // Fecha de registro
}