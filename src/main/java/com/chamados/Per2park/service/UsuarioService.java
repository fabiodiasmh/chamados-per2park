package com.chamados.Per2park.service;

import com.chamados.Per2park.entity.Chamado;
import com.chamados.Per2park.entity.UserLogin;
import com.chamados.Per2park.entity.Usuario;
import com.chamados.Per2park.repository.ChamadoRepository;
import com.chamados.Per2park.repository.UsuarioLogadoRepository;
import com.chamados.Per2park.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ChamadoRepository chamadoRepository;
    private final UsuarioLogadoRepository usuarioLogadoRepository;
    private final ApiService apiService;

    public UsuarioService(UsuarioRepository usuarioRepository, ChamadoRepository chamadoRepository, UsuarioLogadoRepository usuarioLogadoRepository, ApiService apiService) {
        this.usuarioRepository = usuarioRepository;
        this.chamadoRepository = chamadoRepository;
        this.usuarioLogadoRepository = usuarioLogadoRepository;
        this.apiService = apiService;
    }

    public Chamado salva_chamado(Chamado dados) {


        // Busca o usuário existente
        Usuario usuario = usuarioRepository.findById(dados.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        // Verifica se já existe um chamado com o mesmo chamadoId
        Chamado chamadoExistente = chamadoRepository.findByChamadoId(dados.getChamadoId());

        if (chamadoExistente != null) {
            // Atualiza apenas o status (e, se necessário, outros campos)
            chamadoExistente.setStatus(dados.getStatus());
            // Opcional: atualizar usuário também? (se for permitido mudar o usuário do chamado)
            // chamadoExistente.setUsuario(usuario);
            return chamadoRepository.save(chamadoExistente);
        } else {
            // Cria novo chamado
            Chamado novoChamado = new Chamado();
            novoChamado.setChamadoId(dados.getChamadoId());
            novoChamado.setEmail(dados.getEmail());
            novoChamado.setStatus(dados.getStatus());
            novoChamado.setUsuario(usuario);

            return chamadoRepository.save(novoChamado);
        }

    }

    public UserLogin salvarLogin(UserLogin dados){


        return usuarioLogadoRepository.save(dados);
    }

    public List<Chamado> busca_meus_chamados_service(Usuario dados){
        if (dados == null) {
            throw new IllegalArgumentException("ID do usuário não pode ser nulo.");
        }
        System.out.println("Usuario_ID = "+ dados);
       return chamadoRepository.findByUsuarioId(dados.getId());
    }
}