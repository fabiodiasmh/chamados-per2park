package com.chamados.Per2park.service;

import com.chamados.Per2park.controller.RequestDTO.RequestAutentica;
import com.chamados.Per2park.controller.RequestDTO.RequestChamados;
import com.chamados.Per2park.controller.RequestDTO.StatusDTO;
import com.chamados.Per2park.controller.ResponseDTO.ChamadoBaseDTO;
import com.chamados.Per2park.controller.ResponseDTO.ChamadosAgrupadosPorStatusDTO;
import com.chamados.Per2park.controller.ResponseDTO.TokenDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApiService {

//    @Autowired
//    private WebClient webClient;

    @Autowired
    @Qualifier("webClientAuth")
    private WebClient webClientAuth;

    @Autowired
    @Qualifier("webClientChamados")
    private WebClient webClientChamados;

    @Autowired
    private ObjectMapper objectMapper; // Injetado pelo Spring Boot

    public TokenDTO ServiceAutentica(RequestAutentica dados) {
        Object[] responseArray = webClientAuth.post()
                .uri("/api/v0/User/userValidation")
                .bodyValue(dados)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Erro API: " + body)))
//                .bodyToMono(String.class)
//                .block(); // bloqueia até receber resposta (modo síncrono)
                .bodyToMono(Object[].class)
                .block();

        String resultado = responseArray != null ? responseArray[1].toString() : null;
        return new TokenDTO(resultado);

    }

    public List<ChamadoBaseDTO> ServiceGetChamados(String token) {
        RequestChamados requestBody = new RequestChamados();
        requestBody.setListStatus(Arrays.asList(
                new StatusDTO("0", "Opened"),
                new StatusDTO("1", "Under Analysis"),
                new StatusDTO("2", "In Attendance"),
//                new StatusDTO("5", "Closed"),
//                new StatusDTO("6", "Reopened"),
                new StatusDTO("8", "Feedback"),
//                new StatusDTO("9", "Awaiting response"),
                new StatusDTO("10", "Forwarding level 3"),
//                new StatusDTO("11", "Awaiting For Technical Assistance"),
                new StatusDTO("12", "Forwarding level 2")

        ));

        // Faz a chamada e recebe como String (para processar manualmente)
        String jsonResponse = webClientChamados.post()
                .uri("/api/v0/AssistanceCall/getAssistanceCallParam")
                .header("Authorization", "Bearer " + token) // 🔐
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Erro API: " + body)))
                .bodyToMono(String.class)
                .block(); // bloqueia até receber resposta (modo síncrono)

        // Desserializa manualmente para List<ChamadoDTO>
        try {
            return objectMapper.readValue(jsonResponse, new TypeReference<List<ChamadoBaseDTO>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Erro ao desserializar chamados: " + e.getMessage(), e);
        }


    }
    public ChamadosAgrupadosPorStatusDTO serviceGetChamadosAgrupados(String token) {
//        List<ChamadoBaseDTO> chamados = ServiceGetChamados(token); // reutiliza o método acima
//
//        Map<Integer, List<ChamadoBaseDTO>> agrupado = chamados.stream()
//                .collect(Collectors.groupingBy(ChamadoBaseDTO::getStatus));
//
//        return new ChamadosAgrupadosPorStatusDTO(agrupado);

        RequestChamados requestBody = new RequestChamados();
        requestBody.setListStatus(Arrays.asList(
                new StatusDTO("0", "Opened"),
                new StatusDTO("1", "Under Analysis"),
                new StatusDTO("2", "In Attendance"),
//                new StatusDTO("5", "Closed"),
//                new StatusDTO("6", "Reopened"),
                new StatusDTO("8", "Feedback"),
//                new StatusDTO("9", "Awaiting response"),
                new StatusDTO("10", "Forwarding level 3"),
//                new StatusDTO("11", "Awaiting For Technical Assistance"),
                new StatusDTO("12", "Forwarding level 2")

        ));

        // Faz a chamada e recebe como String (para processar manualmente)
        String jsonResponse = webClientChamados.post()
                .uri("/api/v0/AssistanceCall/getAssistanceCallParam")
                .header("Authorization", "Bearer " + token) // 🔐
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Erro API: " + body)))
                .bodyToMono(String.class)
                .block(); // bloqueia até receber resposta (modo síncrono)

        try {
            // 1. Desserializa JSON para lista de ChamadoBaseDTO
            List<ChamadoBaseDTO> chamados = objectMapper.readValue(
                    jsonResponse,
                    new TypeReference<List<ChamadoBaseDTO>>() {}
            );

            // 2. Agrupa por status
            Map<Integer, List<ChamadoBaseDTO>> grouped = chamados.stream()
                    .collect(Collectors.groupingBy(ChamadoBaseDTO::getStatus));

            // 3. Retorna DTO final
            return new ChamadosAgrupadosPorStatusDTO(grouped);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar chamados: " + e.getMessage(), e);
        }
    }
}