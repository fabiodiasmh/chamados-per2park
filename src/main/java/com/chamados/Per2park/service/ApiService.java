package com.chamados.Per2park.service;

import com.chamados.Per2park.controller.RequestDTO.AssistanceCallResponseDTO;
import com.chamados.Per2park.controller.RequestDTO.RequestAutentica;
import com.chamados.Per2park.controller.RequestDTO.RequestChamados;
import com.chamados.Per2park.controller.RequestDTO.StatusDTO;
import com.chamados.Per2park.controller.ResponseDTO.ChamadoBaseDTO;
import com.chamados.Per2park.controller.ResponseDTO.ChamadosAgrupadosPorStatusDTO;
import com.chamados.Per2park.controller.ResponseDTO.TokenDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

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

    public AssistanceCallResponseDTO DetalhesChamadoService(String token, Long id){
            try {
                // Faz a chamada GET e recebe como String (para processar manualmente)
                String jsonResponse = webClientChamados.get()
                        .uri("/api/v0/AssistanceCall/getAssistanceCall/{id}", id)
                        .header("Authorization", "Bearer " + token)
                        .retrieve()
                        .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                                response -> response.bodyToMono(String.class)
                                        .flatMap(body -> Mono.error(new RuntimeException("Erro API ao buscar chamado " + id + ": " + body))))
                        .bodyToMono(String.class)
                        .block(); // modo síncrono

                if (jsonResponse == null || jsonResponse.trim().isEmpty()) {
                    throw new RuntimeException("Resposta vazia ao buscar chamado " + id);
                }
                System.out.println(objectMapper.readValue(jsonResponse, AssistanceCallResponseDTO.class));
                // Desserializa para ChamadoBaseDTO (Jackson ignora campos extras)
                return objectMapper.readValue(jsonResponse, AssistanceCallResponseDTO.class);

            } catch (Exception e) {
                throw new RuntimeException("Erro ao buscar ou desserializar chamado " + id + ": " + e.getMessage(), e);
            }


    }

    public Boolean UpdateChamadoService(String token, AssistanceCallResponseDTO dados) {
        try {
            Boolean apiResponse = webClientChamados
                    .put()
                    .uri("/api/v0/AssistanceCall/updateAssistanceCall")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(dados)
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> response.bodyToMono(String.class) // ← "response" aqui é o parâmetro da lambda (ok!)
                                    .flatMap(body -> Mono.error(new RuntimeException("Erro API ao atualizar chamado: " + body)))
                    )
                    .bodyToMono(Boolean.class) // <--- aqui pegamos qualquer tipo
                    .block();

            return apiResponse; // ← nome único

        } catch (Exception e) {
            throw new RuntimeException("Falha ao atualizar chamado", e);
        }
    }
    public TokenDTO ServiceAutentica(RequestAutentica dados) {

        try {
            // chamada WebClient...
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

            if (responseArray == null || responseArray.length < 2) {
                throw new RuntimeException("Resposta inválida");
            }

//            String resultado = responseArray != null ? responseArray[1].toString() : null;
//            return new TokenDTO(resultado);
            // Converte o primeiro item (usuário) para JsonNode
            JsonNode userNode = objectMapper.convertValue(responseArray[0], JsonNode.class);
            String token = responseArray[1].toString();
            System.out.println(userNode);
            return new TokenDTO(token, userNode);

        } catch (WebClientResponseException.Unauthorized ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas para o serviço externo");
        } catch (WebClientResponseException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Erro no serviço externo: " + ex.getResponseBodyAsString());
        }


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
                new StatusDTO("9", "Awaiting response"),
                new StatusDTO("10", "Forwarding level 3"),
                new StatusDTO("11", "Awaiting For Technical Assistance"),
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


        RequestChamados requestBody = new RequestChamados();
        requestBody.setListStatus(Arrays.asList(
                new StatusDTO("0", "Opened"),
                new StatusDTO("1", "Under Analysis"),
                new StatusDTO("2", "In Attendance"),
//                new StatusDTO("5", "Closed"),
//                new StatusDTO("6", "Reopened"),
                new StatusDTO("8", "Feedback"),
                new StatusDTO("9", "Awaiting response"),
                new StatusDTO("10", "Forwarding level 3"),
                new StatusDTO("11", "Awaiting For Technical Assistance"),
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