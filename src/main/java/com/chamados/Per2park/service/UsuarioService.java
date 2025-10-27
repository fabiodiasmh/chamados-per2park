package com.chamados.Per2park.service;

import com.chamados.Per2park.entity.User;
import com.chamados.Per2park.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public User salvar(User usuario) {
        return usuarioRepository.save(usuario);
    }
}