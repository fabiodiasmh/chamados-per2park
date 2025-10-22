package com.chamados.Per2park.service;

import com.chamados.Per2park.controller.RequestDTO.AssistanceCallResponseDTO;
import com.chamados.Per2park.controller.RequestDTO.UserSATDTO;
import com.chamados.Per2park.controller.ResponseDTO.TokenSatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ApiSAT {

    @Autowired
    @Qualifier("webClientSAT")
    private WebClient webClientSAT;

    public TokenSatDTO autenticaSatService(UserSATDTO dados) {
        try {
            return webClientSAT.post()
                    .uri("/Usuario/Login")
                    .bodyValue(dados)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> response.bodyToMono(String.class)
                                    .flatMap(body -> Mono.error(new RuntimeException("Erro na autenticação SAT: " + body))))
                    .bodyToMono(TokenSatDTO.class) // ← Desserializa diretamente para TokenSatDTO
                    .block();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao autenticar no serviço SAT: " + e.getMessage(), e);
        }
    }



    public void verificaSerieSatService(TokenSatDTO dados, String numSerie) {
        try {
            return webClientSAT.get()
                    .uri("/EquipamentoContrato?sortActive=numSerie&sortDirection=asc&codClientes=755&pageSize=50&filter="+numSerie)

                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> response.bodyToMono(String.class)
                                    .flatMap(body -> Mono.error(new RuntimeException("Erro na autenticação SAT: " + body))))
                    .bodyToMono(TokenSatDTO.class) // ← Desserializa diretamente para TokenSatDTO
                    .block();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao autenticar no serviço SAT: " + e.getMessage(), e);
        }
    }



}
