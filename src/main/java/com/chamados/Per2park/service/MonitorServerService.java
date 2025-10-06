package com.chamados.Per2park.service;

import com.chamados.Per2park.controller.RequestDTO.MonitorServerRequestDTO;
import com.chamados.Per2park.controller.ResponseDTO.MonitorServerResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;

@Service
public class MonitorServerService {

    @Autowired
    @Qualifier("webClientMonitoramento")
    private WebClient webClientMonitoramento;

    public List<MonitorServerResponseDTO> monitoramentoReplicacaoService(String token) {
        List<MonitorServerRequestDTO> resposta = webClientMonitoramento.get()
                .uri("/api/v0/Location/GetUnities")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new RuntimeException("Erro na API: " + body)))
                )
                .bodyToFlux(MonitorServerRequestDTO.class)
                .collectList()
                .block();

        if (resposta == null) {
            return Collections.emptyList();
        }

        List<MonitorServerResponseDTO> resultado = resposta.stream()
                .filter(a -> a.getReplicacao() != null && a.getReplicacao().getPendencies() != null)
                .filter(q -> q.getReplicacao().getPendencies() > 1)
                .filter(status -> status.getStatus() == 1)
                .map(fa -> new MonitorServerResponseDTO(
                        fa.getNomeUnidade(),
                        fa.getUploadDate(),
                        fa.getNomeCliente(),
                        fa.getReplicacao()

                ))
                .collect(Collectors.toList());

//         ✅ Corrigido: usa getPendencias(), não getReplicacao()
        resultado.forEach(w ->
                System.out.println("Nome Unidade: " + w.getNomeUnidade() + ": " + w.getReplicacao().getPendencies())
        );

        return resultado;
    }

    public List<MonitorServerRequestDTO> monitoramentoServerGeralService(String token) {
        List<MonitorServerRequestDTO> resposta = webClientMonitoramento.get()
                .uri("/api/v0/Location/GetUnities")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new RuntimeException("Erro na API: " + body)))
                )
                .bodyToFlux(MonitorServerRequestDTO.class)
                .collectList()
                .block();


        resposta.forEach(w ->
                System.out.println(": " + w.getNomeUnidade() + ": " + w.getReplicacao().getPendencies())
        );

        return resposta;

    }
}
