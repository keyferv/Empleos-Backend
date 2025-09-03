package com.example.empleos.service.interfaces;

import com.example.empleos.presentation.dto.request.UsuarioRequestDTO;
import com.example.empleos.presentation.dto.response.UsuarioResponseDTO;

import java.util.List;


public interface UsuarioServices {

    // UsuarioRequestDTO es para recibir los datos del usuario

    UsuarioResponseDTO addUsuario(UsuarioRequestDTO usuarioRequestDTO);;
    UsuarioResponseDTO updateUsuario(Integer idUsuario, UsuarioRequestDTO usuarioRequestDTO);
    String deleteUsuario(Integer idUsuario);
    UsuarioResponseDTO getUsuarioById(Integer idUsuario);

    // UsuarioResponseDTO es para enviar los datos del usuario
    List<UsuarioResponseDTO> getAllUsuarios();
    List<UsuarioResponseDTO> getUsuariosByEstado(Integer estadoId);
    List<UsuarioResponseDTO> getUsuariosByRole(Integer roleId);
    List<UsuarioResponseDTO> getUsuariosByEstadoAndRole(Integer estadoId, Integer roleId);
}
