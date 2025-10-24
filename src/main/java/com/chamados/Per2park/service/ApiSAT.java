package com.chamados.Per2park.service;

import com.chamados.Per2park.controller.RequestDTO.AssistanceCallResponseDTO;
import com.chamados.Per2park.controller.RequestDTO.UserSATDTO;
import com.chamados.Per2park.controller.ResponseDTO.SatEquipamentoDTO;
import com.chamados.Per2park.controller.ResponseDTO.SatEquipamentoResponse;
import com.chamados.Per2park.controller.ResponseDTO.TokenSatDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ApiSAT {

    @Autowired
    @Qualifier("webClientSAT")
    private WebClient webClientSAT;

    @Autowired
    private ObjectMapper objectMapper; // Já configurado pelo Spring Boot

    public TokenSatDTO autenticaSatService() {


        try {
            Map<String, Object> jsonBody = Map.of(
                    "codUsuario", "fabio.dias",
                    "senha", "Perto@2024"


            );


            return webClientSAT.post()
                    .uri("/Usuario/Login")
                    .bodyValue(jsonBody)
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



    // ⚠️ Não declare como static!
    public List<SatEquipamentoDTO> verificaSerieSatService(TokenSatDTO token, String numSerie) {
        System.out.println("token: "+token.getToken());
        System.out.println("numero de serie: "+numSerie);
        try {
            SatEquipamentoResponse responseSAT = webClientSAT.get()
                    .uri("/EquipamentoContrato?codClientes=755,521,573,559,489,534,532,140,132&pageSize=50&filter=" + numSerie)
                    .header("Authorization", "Bearer " + token.getToken()) // 🔐
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> response.bodyToMono(String.class)
                                    .flatMap(body -> Mono.error(new RuntimeException("Erro na autenticação SAT: " + body))))
                    .bodyToMono(SatEquipamentoResponse.class)
                    .block();

            System.out.println(responseSAT != null ? responseSAT.getItems() : List.of());
            return responseSAT != null ? responseSAT.getItems() : List.of();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao autenticar no serviço SAT: " + e.getMessage(), e);
        }
    }



}
