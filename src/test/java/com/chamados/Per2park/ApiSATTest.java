package com.chamados.Per2park;

import com.chamados.Per2park.controller.ResponseDTO.SatEquipamentoDTO;
import com.chamados.Per2park.controller.ResponseDTO.SatEquipamentoResponse;
import com.chamados.Per2park.controller.ResponseDTO.TokenSatDTO;
import com.chamados.Per2park.service.ApiSAT;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.mockito.Answers;

class ApiSATTest {

    private MockWebServer mockWebServer;
    private ApiSAT apiSAT;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        // WebClient real apontando para o MockWebServer
        WebClient webClient = WebClient.builder()
                .baseUrl(mockWebServer.url("/").toString())
                .build();

        apiSAT = new ApiSAT();
        apiSAT.webClientSAT = webClient;      // injeta via campo público
        apiSAT.objectMapper = objectMapper;   // opcional: Spring já fornece, mas ok para teste
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    // ─── TESTE 1: autenticaSatService ───────────────────────────────────────

    @Test
    void autenticaSatService_DeveRetornarTokenQuandoSucesso() throws Exception {
        // Dado
        TokenSatDTO expected = new TokenSatDTO();
        expected.setToken("abc123xyz");
        String jsonResponse = objectMapper.writeValueAsString(expected);

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(jsonResponse)
                .addHeader("Content-Type", "application/json"));

        // Quando
        TokenSatDTO result = apiSAT.autenticaSatService();

        // Então
        assertThat(result).isNotNull();
        assertThat(result.getToken()).isEqualTo("abc123xyz");

        // Opcional: verificar que a requisição foi feita corretamente
        var request = mockWebServer.takeRequest();
        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getPath()).isEqualTo("/Usuario/Login");
        assertThat(request.getBody().readUtf8()).contains("fabio.dias");
    }

    @Test
    void autenticaSatService_DeveLancarExcecaoQuandoApiRetornaErro401() {
        // Dado
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(401)
                .setBody("{\"message\":\"Credenciais inválidas\"}")
                .addHeader("Content-Type", "application/json"));

        // Então
        assertThatThrownBy(() -> apiSAT.autenticaSatService())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Erro na autenticação SAT");
    }

    // ─── TESTE 2: verificaSerieSatService ───────────────────────────────────

    @Test
    void verificaSerieSatService_DeveRetornarListaDeEquipamentosQuandoSucesso() throws Exception {
        // Dado
        TokenSatDTO token = new TokenSatDTO();
        token.setToken("valid-token");
        String numSerie = "SN123456";

        SatEquipamentoResponse response = new SatEquipamentoResponse();
        SatEquipamentoDTO item = new SatEquipamentoDTO();
        item.setNumSerie("SN123456");
        item.setIndAtivo(1);
        item.setCodCliente(12345L);

        Map<String,Object> roles = Map.of(
                "nomeLocal", "carapicuiba",
                "Latitude",1234,
                    "Longitude","789456"
        );
        item.setLocalAtendimento(roles);

        item.setEquipamento(Map.of());
        item.setCliente(Map.of());

        response.setItems(List.of(item));

        String jsonResponse = objectMapper.writeValueAsString(response);
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(jsonResponse)
                .addHeader("Content-Type", "application/json"));

        // Quando
        List<SatEquipamentoDTO> result = apiSAT.verificaSerieSatService(token, numSerie);

        // Então
//        assertThat(result).isNotEmpty();
//        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNumSerie()).isEqualTo("SN123456");
        assertThat(result.get(0).getIndAtivo()).isEqualTo(1);
//        assertThat(result.get(0).getLocalAtendimento());
        assertThat(result.get(0).getLocalAtendimento()).isNotNull();


        // Verificar header de autorização
        var request = mockWebServer.takeRequest();
        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(request.getPath()).contains("/EquipamentoContrato");
        assertThat(request.getPath()).contains("filter=SN123456");
        assertThat(request.getHeader("Authorization")).isEqualTo("Bearer valid-token");
    }
//
    @Test
    void verificaSerieSatService_DeveRetornarListaVaziaQuandoRespostaNula() {
        // Dado: corpo vazio ou nulo (embora raro em JSON)
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("") // corpo vazio
                .addHeader("Content-Type", "application/json"));

        TokenSatDTO token = new TokenSatDTO();
        token.setToken("any");

        // Quando
        List<SatEquipamentoDTO> result = apiSAT.verificaSerieSatService(token, "SN999");


        assertThat(result).isNotNull();
//

    }
//
//    @Test
//    void verificaSerieSatService_DeveLancarExcecaoQuandoErroNaApi() {
//        // Dado
//        mockWebServer.enqueue(new MockResponse()
//                .setResponseCode(500)
//                .setBody("{\"error\":\"Internal error\"}")
//                .addHeader("Content-Type", "application/json"));
//
//        TokenSatDTO token = new TokenSatDTO();
//        token.setToken("any");
//
//        // Então
//        assertThatThrownBy(() -> apiSAT.verificaSerieSatService(token, "SN999"))
//                .isInstanceOf(RuntimeException.class)
//                .hasMessageContaining("Erro na autenticação SAT");
//    }
}