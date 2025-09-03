package com.example.empleos.service.implementation;

import com.example.empleos.persistence.entity.Estado;
import com.example.empleos.persistence.entity.Roles;
import com.example.empleos.persistence.entity.RolesEnum;
import com.example.empleos.persistence.entity.Usuarios;
import com.example.empleos.persistence.repository.EstadoRepository;
import com.example.empleos.persistence.repository.RoleRepository;
import com.example.empleos.persistence.repository.UsuarioRepository;
import com.example.empleos.presentation.dto.request.UsuarioRequestDTO;
import com.example.empleos.presentation.dto.response.UsuarioResponseDTO;
import com.example.empleos.service.interfaces.UsuarioServices;
import com.example.empleos.utility.Mapper.UsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Primary
public class UsuarioImpl implements UsuarioServices {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UsuarioResponseDTO addUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        Estado estado = estadoRepository.findById(usuarioRequestDTO.getEstadoId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid estado ID"));
        Set<Roles> roles = usuarioRequestDTO.getRoleIds().stream()
                .map(roleId -> roleRepository.findById(roleId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid role ID")))
                .collect(Collectors.toSet());

        // 🔐 Cifrar la contraseña antes de guardar
        String passwordEncriptada = passwordEncoder.encode(usuarioRequestDTO.getPassword());

        // Mapear a entidad con la contraseña cifrada
        Usuarios usuarios = UsuarioMapper.toEntity(usuarioRequestDTO, estado, roles);
        usuarios.setPassword(passwordEncriptada);

        // Guardar en la base de datos
        usuarios = usuarioRepository.save(usuarios);

        // Mapear a DTO de respuesta
        return UsuarioMapper.toDto(usuarios);
    }

    @Override
    public UsuarioResponseDTO updateUsuario(Integer idUsuario, UsuarioRequestDTO usuarioRequestDTO) {
        Usuarios usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Invalid usuario ID: " + idUsuario + " no existe"));

        // 📌 Actualizar el estado por nombre
        Estado estado = estadoRepository.findByType(usuarioRequestDTO.getEstado().getType())
                .orElseThrow(() -> new IllegalArgumentException("Invalid estado: " + usuarioRequestDTO.getEstado()));
        usuario.setEstado(estado);

        // 🔄 Manejar los roles (añadir y eliminar por nombre)
        Set<Roles> nuevosRoles = usuarioRequestDTO.getRoles().stream()
                .map(rolesEnum -> {
                    try {
                        // Validar el nombre del rol con el ENUM
                        RolesEnum rolesEnumValue = RolesEnum.valueOf(rolesEnum);
                        return roleRepository.findByRolesEnum(rolesEnumValue)
                                .orElseThrow(() -> new IllegalArgumentException("Invalid role: " + rolesEnum));
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("Invalid role: " + rolesEnum);
                    }
                })
                .collect(Collectors.toSet());

        // Limpiar roles actuales y añadir los nuevos
        usuario.getRoles().clear();         // Limpiar roles actuales
        usuario.getRoles().addAll(nuevosRoles); // Añadir nuevos roles

        // 🔐 Actualizar la contraseña (si es diferente y no está vacía)
        if (usuarioRequestDTO.getPassword() != null && !usuarioRequestDTO.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuarioRequestDTO.getPassword()));
        }

        // 🔄 Actualizar otros campos
        usuario.setName(usuarioRequestDTO.getName());
        usuario.setLastname(usuarioRequestDTO.getLastname());
        usuario.setUsername(usuarioRequestDTO.getUsername());
        usuario.setBirthDate(usuarioRequestDTO.getBirthDate());
        usuario.setJobTitle(usuarioRequestDTO.getJobTitle());
        usuario.setPhone(usuarioRequestDTO.getPhone());
        usuario.setCertifications(usuarioRequestDTO.getCertifications());
        usuario.setEmail(usuarioRequestDTO.getEmail());

        // 💾 Guardar los cambios
        usuario = usuarioRepository.save(usuario);

        // 🔍 Devolver el usuario actualizado como DTO
        return UsuarioMapper.toDto(usuario);
    }


    @Override
    public String deleteUsuario(Integer idUsuario) {
    Usuarios usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new IllegalArgumentException("Invalid usuario ID:" + idUsuario + " no existe"));
    usuarioRepository.delete(usuario);
    // mostrar mensaje de eliminacion
    return "El Usuario con el ID: " + idUsuario + " ha sido eliminado"
            + "De Nombre: " + usuario.getName();
    }

    @Override
    public UsuarioResponseDTO getUsuarioById(Integer idUsuario) {
        Usuarios usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Invalid usuario ID:" + idUsuario + " no existe"));
        return UsuarioMapper.toDto(usuario);
    }


    @Override
    public List<UsuarioResponseDTO> getAllUsuarios() {
        List<Usuarios> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(UsuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioResponseDTO> getUsuariosByEstado(Integer estadoId) {
        return null;
    }

    @Override
    public List<UsuarioResponseDTO> getUsuariosByRole(Integer roleId) {
        List<Usuarios> usuarios = usuarioRepository.findByRolesId(roleId);
        return usuarios.stream()
                .map(UsuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioResponseDTO> getUsuariosByEstadoAndRole(Integer estadoId, Integer roleId) {
        return null;
    }
}
