package com.chamados.Per2park.service;

import com.chamados.Per2park.controller.RequestDTO.StatusDTO;
import com.chamados.Per2park.entity.*;
import com.chamados.Per2park.repository.ChamadoRepository;
import com.chamados.Per2park.repository.StatusChamadoRepository;
import com.chamados.Per2park.repository.UsuarioLogadoRepository;
import com.chamados.Per2park.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final StatusChamadoRepository statusChamadoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ChamadoRepository chamadoRepository;
    private final UsuarioLogadoRepository usuarioLogadoRepository;
    private final ApiService apiService;

    public UsuarioService(StatusChamadoRepository statusChamadoRepository, UsuarioRepository usuarioRepository, ChamadoRepository chamadoRepository, UsuarioLogadoRepository usuarioLogadoRepository, ApiService apiService) {
        this.statusChamadoRepository = statusChamadoRepository;
        this.usuarioRepository = usuarioRepository;
        this.chamadoRepository = chamadoRepository;
        this.usuarioLogadoRepository = usuarioLogadoRepository;
        this.apiService = apiService;
    }

//    public Chamado salva_chamado(Chamado dados) {
//
//
//        // Busca o usuário existente
//        Usuario usuario = usuarioRepository.findById(dados.getUsuario().getId())
//                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
//
//        // Verifica se já existe um chamado com o mesmo chamadoId
//        Chamado chamadoExistente = chamadoRepository.findByChamadoId(dados.getChamadoId());
//
//        if (chamadoExistente != null) {
//            // Atualiza apenas o status (e, se necessário, outros campos)
////            chamadoExistente.setStatus(dados.getStatus());
//            // Opcional: atualizar usuário também? (se for permitido mudar o usuário do chamado)
//            // chamadoExistente.setUsuario(usuario);
//            return chamadoRepository.save(chamadoExistente);
//        } else {
//            // Cria novo chamado
//            Chamado novoChamado = new Chamado();
//            novoChamado.setChamadoId(dados.getChamadoId());
//            novoChamado.setEmail(dados.getEmail());
//            novoChamado.setStatusChamado();
////            new StatusChamadoDTO(c.getStatusChamado().getId(), c.getStatusChamado().getName()),
//            novoChamado.setUsuario(usuario);
//
//            return chamadoRepository.save(novoChamado);
//        }
//
//    }

//public Chamado salvaChamado(ChamadoInputDTO dados) {
//    // Validação básica
//    if (dados.getChamadoId() == null) {
//        throw new IllegalArgumentException("chamadoId é obrigatório");
//    }
//    if (dados.getStatusId() == null) {
//        throw new IllegalArgumentException("statusId é obrigatório");
//    }
//    if (dados.getUsuario() == null || dados.getUsuario().getId() == null) {
//        throw new IllegalArgumentException("usuario.id é obrigatório");
//    }
//
//    // Busca relacionamentos
//    Usuario usuario = usuarioRepository.findById(dados.getUsuario().getId())
//            .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
//
//    StatusChamado status = statusChamadoRepository.findById(dados.getStatusId())
//            .orElseThrow(() -> new RuntimeException("Status de chamado não encontrado!"));
//
//    // Verifica se o chamado já existe
//    Chamado chamado = chamadoRepository.findByChamadoId(dados.getChamadoId());
//
//    if (chamado == null) {
//        // Cria novo
//        chamado = new Chamado();
//        chamado.setChamadoId(dados.getChamadoId());
//    }
//
//    // Atualiza dados (tanto para novo quanto existente)
//    chamado.setEmail(dados.getEmail());
//    chamado.setStatusChamado(status);
//    chamado.setUsuario(usuario);
//
//    return chamadoRepository.save(chamado);
//}

public Chamado salvaChamado(ChamadoInputDTO dados) {

    if (dados.getChamadoId() == null) {
        throw new IllegalArgumentException("chamadoId é obrigatório");
    }
    if (dados.getStatusId() == null) {
        throw new IllegalArgumentException("statusId é obrigatório");
    }
    if (dados.getUsuario() == null || dados.getUsuario().getId() == null) {
        throw new IllegalArgumentException("usuario.id é obrigatório");
    }

    // Busca relacionamentos
    Usuario usuario = usuarioRepository.findById(dados.getUsuario().getId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

    StatusChamado status = statusChamadoRepository.findById(dados.getStatusId())
            .orElseThrow(() -> new RuntimeException("Status de chamado não encontrado!"));

    // ➜ Sempre criar um novo
    Chamado chamado = new Chamado();

    chamado.setChamadoId(dados.getChamadoId());
    chamado.setEmail(dados.getEmail());
    chamado.setStatusChamado(status);
    chamado.setUsuario(usuario);

    return chamadoRepository.save(chamado);
}


    public UserLogin salvarLogin(UserLogin dados){


        return usuarioLogadoRepository.save(dados);
    }

    public List<ChamadoDTO> buscaMeusChamadosDto(Long usuarioId) {
        if (usuarioId == null) {
            throw new IllegalArgumentException("ID do usuário não pode ser nulo.");
        }

        return chamadoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(chamado -> new ChamadoDTO(
                        chamado.getChamadoId(),
                        chamado.getEmail(),
//                        chamado.getStatusChamado(),
                        chamado.getDataCriacao(),
                        chamado.getUsuario().getId(),                       // assumindo que Usuario tem getId()
                        chamado.getUsuario().getNome() != null ? chamado.getUsuario().getNome() : ""
                ))
                .toList();
    }
}