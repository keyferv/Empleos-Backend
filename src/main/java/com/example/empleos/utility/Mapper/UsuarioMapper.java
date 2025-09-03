package com.example.empleos.utility.Mapper;

import com.example.empleos.persistence.entity.Estado;
import com.example.empleos.persistence.entity.Roles;
import com.example.empleos.persistence.entity.Usuarios;
import com.example.empleos.presentation.dto.request.UsuarioRequestDTO;
import com.example.empleos.presentation.dto.response.UsuarioResponseDTO;

import java.util.Set;
import java.util.stream.Collectors;

public class UsuarioMapper {

    // ✅ De DTO a Entidad
    public static Usuarios toEntity(UsuarioRequestDTO dto, Estado estado, Set<Roles> roles) {
        return Usuarios.builder()
                .name(dto.getName())
                .lastname(dto.getLastname())
                .email(dto.getEmail())
                .password(dto.getPassword()) // Encripta antes de guardar
                .username(dto.getUsername())
                .birthDate(dto.getBirthDate())
                .jobTitle(dto.getJobTitle())
                .phone(dto.getPhone())
                .certifications(dto.getCertifications())
                .estado(estado)
                .roles(roles)
                .build();
    }

    // ✅ De Entidad a DTO
    public static UsuarioResponseDTO toDto(Usuarios usuario) {
        return UsuarioResponseDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .name(usuario.getName())
                .lastname(usuario.getLastname())
                .email(usuario.getEmail())
                .username(usuario.getUsername())
                .birthDate(usuario.getBirthDate())
                .jobTitle(usuario.getJobTitle())
                .phone(usuario.getPhone())
                .certifications(usuario.getCertifications())
                .isEnabled(usuario.isEnabled())
                .accountNoExpired(usuario.isAccountNoExpired())
                .accountNoLocked(usuario.isAccountNoLocked())
                .credentialNoExpired(usuario.isCredentialNoExpired())
                .estado(usuario.getEstado().getType().name()) // Estado como String
                .roles(usuario.getRoles().stream()
                        .map(role -> role.getRolesEnum().name()) // Convertir RolesEnum a String
                        .collect(Collectors.toSet()))
                .dateRegister(usuario.getDateRegister())
                .build();
    }
}
