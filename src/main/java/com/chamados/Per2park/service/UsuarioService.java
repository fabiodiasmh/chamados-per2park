package com.chamados.Per2park.service;

import com.chamados.Per2park.controller.UserPgDTO;
import com.chamados.Per2park.entity.User;
import com.chamados.Per2park.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ApiService apiService;

    public UsuarioService(UsuarioRepository usuarioRepository, ApiService apiService) {
        this.usuarioRepository = usuarioRepository;
        this.apiService = apiService;
    }

    public User salvar(User dados) {
        if (usuarioRepository.existsById(dados.getChamado())) {
            throw new RuntimeException("Já existe um usuário com o chamado " + dados.getChamado());
        }

        return usuarioRepository.save(dados);
    }
}