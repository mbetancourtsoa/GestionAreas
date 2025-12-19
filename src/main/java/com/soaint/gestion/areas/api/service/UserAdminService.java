package com.soaint.gestion.areas.api.service;

import org.springframework.stereotype.Service;

import com.soaint.gestion.areas.api.dto.AreaUsuarioDto;
import com.soaint.gestion.areas.api.repository.UsuarioAreaRepository;
import com.soaint.gestion.areas.api.repository.UsuarioRepository;


@Service
public class UserAdminService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioAreaRepository usuarioAreaRepository;

    public UserAdminService(
            UsuarioRepository usuarioRepository,
            UsuarioAreaRepository usuarioAreaRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioAreaRepository = usuarioAreaRepository;
    }


    public AreaUsuarioDto obtenerAreaUsuario(String username) {

        String area = usuarioAreaRepository.findAreaByUsername(username);

        if (area == null) {
            throw new RuntimeException("No se encontró el área del usuario.");
        }

        return new AreaUsuarioDto(area);
    }
}