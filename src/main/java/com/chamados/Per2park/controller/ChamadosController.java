package com.chamados.Per2park.controller;

import com.chamados.Per2park.controller.RequestDTO.AssistanceCallResponseDTO;
import com.chamados.Per2park.controller.RequestDTO.MonitorServerRequestDTO;
import com.chamados.Per2park.controller.RequestDTO.RequestAutentica;
import com.chamados.Per2park.controller.RequestDTO.UserSATDTO;
import com.chamados.Per2park.controller.ResponseDTO.*;
import com.chamados.Per2park.service.ApiSAT;
import com.chamados.Per2park.service.ApiService;
import com.chamados.Per2park.service.MonitorServerService;
import com.chamados.Per2park.service.SeparaStatusChamados;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

//import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")

@Tag(name = "Chamados", description = "Endpoints para gestão de chamados e monitoramento de servidores")
public class ChamadosController {

    private final ApiService apiService;
    private final SeparaStatusChamados separaStatusChamados;
    private final MonitorServerService monitorServerService;
    private final ApiSAT apiSAT;

    public ChamadosController(ApiService apiService, SeparaStatusChamados separaStatusChamados, MonitorServerService monitorServerService, ApiSAT apiSAT) {
        this.apiService = apiService;
        this.separaStatusChamados = separaStatusChamados;
        this.monitorServerService = monitorServerService;
        this.apiSAT = apiSAT;
    }

    // ✅ Login (sem autenticação prévia)
    @Operation(
            summary = "Autenticação do usuário",
            description = "Realiza login no sistema e retorna um token JWT de acesso para uso nas demais rotas."


    )
    @PostMapping("/login")
    public ResponseEntity<?> autentica(@RequestBody RequestAutentica dados) {

        TokenDTO resp = apiService.ServiceAutentica(dados);

        return ResponseEntity.ok(resp);

    }


    // ✅ Listar todos os chamados (requer Bearer token)
    @Operation(
            summary = "Lista todos os chamados",
            description = "Retorna a lista completa de chamados do usuário autenticado. Requer token JWT válido no header `Authorization: Bearer <token>`.",
            security = @SecurityRequirement(name = "bearerAuth")
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "Lista de chamados retornada com sucesso",
//                            content = @Content(mediaType = "application/json",
//                                    array = @ArraySchema(schema = @Schema(implementation = ChamadoBaseDTO.class)))),
//                    @ApiResponse(responseCode = "401", description = "Token ausente, inválido ou expirado",
//                            headers = @Header(name = "WWW-Authenticate", description = "Ex: Bearer realm=\"api\"")),
//                    @ApiResponse(responseCode = "502", description = "Falha na comunicação com o serviço externo de chamados")
//            }
    )
    @Parameter(
            name = "Authorization",
            description = "Token JWT no formato `Bearer <token>`",
            in = ParameterIn.HEADER,
            required = true,
            schema = @Schema(type = "string", example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.xxxxx")
    )
    @GetMapping("/chamados")
    public ResponseEntity<List<ChamadoBaseDTO>> getChamados(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        // 🔹 Validação explícita do header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .header("WWW-Authenticate", "Bearer realm=\"api\"")
                    .body(null);
        }

        String token = authHeader.substring(7).trim();

        if (token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .header("WWW-Authenticate", "Bearer realm=\"api\"")
                    .body(null);
        }

        try {
            // 🔹 Executa chamada para serviço externo
            List<ChamadoBaseDTO> chamados = apiService.ServiceGetChamados(token);
            return ResponseEntity.ok(chamados);

        } catch (ResponseStatusException ex) {
            // Propaga erros já formatados (ex: 401 vindo do serviço externo)
            return ResponseEntity.status(ex.getStatusCode())
                    .body(null);

        } catch (RuntimeException ex) {
            // Log detalhado (sem expor stack trace ao cliente)
            System.err.println("Erro ao buscar chamados com token: " +
                    (token.length() > 10 ? token.substring(0, 10) + "..." : token));
            ex.printStackTrace();

            // Retorna 502 Bad Gateway (já que falhou comunicação com API upstream)
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(null);
        }
    }

    // 🔹 Método auxiliar privado (opcional, já que a lógica está inline acima)
    private String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7).trim();
        }
        return null;
    }

//    @GetMapping("/chamados")
//    public ResponseEntity<List<ChamadoBaseDTO>> RecebeRequestBody(@RequestHeader("Authorization") String authHeader) {
////        String token = (String) session.getAttribute("TOKEN_USUARIO");
//        // Extrai "Bearer abc123" → "abc123"
//        String token = extractToken(authHeader);
//
//        if (token == null || token.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .build();
//        }
//
//
//        List<ChamadoBaseDTO> chamados = apiService.ServiceGetChamados(token);
//        return ResponseEntity.ok(chamados);
//    }

//    @GetMapping("/chamadosPorStatus")
//    public ResponseEntity<Map<String, Long>> chamadosPorStatus(HttpSession session) {
//        String token = (String) session.getAttribute("TOKEN_USUARIO");
//        Map<String, Long> a = separaStatusChamados.getTotalQuantidadePorStatus(token);

    /// /        a.forEach((Nome, quantidade) -> System.out.println(Nome + ": " + quantidade));
//
//        return ResponseEntity.ok(a);
//    }
// ✅ Quantidade de chamados por status
    @Operation(
            summary = "Contagem de chamados por status",
            description = "Retorna um mapa com a quantidade de chamados em cada status (ex: 'ABERTO', 'EM ANDAMENTO', 'FECHADO').",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "Mapa de contagem retornado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Map.class, example = """
                            {
                              "ABERTO": 12,
                              "EM ANDAMENTO": 5,
                              "FECHADO": 30
                            }
                            """)))
    @ApiResponse(responseCode = "401", description = "Não autorizado")
    @ApiResponse(responseCode = "502", description = "Erro ao consultar serviço externo")
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, required = true,
            schema = @Schema(type = "string", example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.xxxxx"))

    @GetMapping("/chamadosPorStatus")
    public ResponseEntity<Map<String, Long>> chamadosPorStatus(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        // 🔹 Validação do token no header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .header("WWW-Authenticate", "Bearer realm=\"api\"")
                    .body(null);
        }

        String token = authHeader.substring(7).trim();
        if (token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .header("WWW-Authenticate", "Bearer realm=\"api\"")
                    .body(null);
        }

        try {
            Map<String, Long> resultado = separaStatusChamados.getTotalQuantidadePorStatus(token);
            return ResponseEntity.ok(resultado);

        } catch (ResponseStatusException ex) {
            // Propaga erros de autorização/validação da camada de serviço
            return ResponseEntity.status(ex.getStatusCode()).body(null);

        } catch (Exception ex) {
            // Log seguro (não expõe token completo)
            System.err.println("Erro em /chamadosPorStatus com token: " +
                    (token.length() > 10 ? token.substring(0, 10) + "..." : token));
            ex.printStackTrace();

            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
        }
    }

    @GetMapping("/status")
    public ResponseEntity<List<ChamadoBaseDTO>> status(
            @RequestParam int valor,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        String token = extractAndValidateToken(authHeader);
        try {
            List<ChamadoBaseDTO> result = separaStatusChamados.getChamadosPorStatus(token, valor);
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            logError("Erro em /status", token, ex);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
        }
    }

    @GetMapping("/locais")
    public ResponseEntity<List<Map.Entry<String, Long>>> locais(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        String token = extractAndValidateToken(authHeader);
        try {
            List<Map.Entry<String, Long>> result = separaStatusChamados.getTop10Locais(token);
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            logError("Erro em /locais", token, ex);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
        }
    }

    @GetMapping("/replicacao")
    public ResponseEntity<List<MonitorServerResponseDTO>> monitorServer(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        String token = extractAndValidateToken(authHeader);
        try {
            List<MonitorServerResponseDTO> result = monitorServerService.monitoramentoReplicacaoService(token);
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            logError("Erro em /replicacao", token, ex);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
        }
    }

    @GetMapping("/geral")
    public ResponseEntity<List<MonitorServerRequestDTO>> monitorServerGeral(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        String token = extractAndValidateToken(authHeader);
        try {
            List<MonitorServerRequestDTO> result = monitorServerService.monitoramentoServerGeralService(token);
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            logError("Erro em /geral", token, ex);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
        }
    }

    @GetMapping("/detalhes_chamado/{id}")
    public ResponseEntity<AssistanceCallResponseDTO> status_chamado(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        String token = extractAndValidateToken(authHeader);
        try {
            AssistanceCallResponseDTO result = apiService.DetalhesChamadoService(token, id);
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            logError("Erro em /detalhes_chamado/" + id, token, ex);
            if (ex instanceof ResponseStatusException) {
                return ResponseEntity.status(((ResponseStatusException) ex).getStatusCode()).body(null);
            }
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
        }
    }

    @PutMapping("/update_chamado")
    public ResponseEntity<String> update_chamado(
            @RequestBody AssistanceCallResponseDTO dados,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        System.out.println("inicio update_chamado");
        String token = extractAndValidateToken(authHeader);
        try {
            System.out.println("estou no try do update chamados service");
            Boolean atualizado = apiService.UpdateChamadoService(token, dados);
            if (Boolean.TRUE.equals(atualizado)) {
                System.out.println("if do update chamado");
                return ResponseEntity.ok("Chamado atualizado com sucesso");

            } else {
                System.out.println("else do update chamado");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Falha ao atualizar chamado");
            }
        } catch (Exception ex) {
            logError("Erro em /update_chamado", token, ex);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Erro ao comunicar com serviço externo");
        }
    }

    private String extractAndValidateToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token ausente ou inválido",
                    new RuntimeException("Missing or invalid Authorization header"));
        }
        String token = authHeader.substring(7).trim();
        if (token.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token vazio");
        }
        return token;
    }

    private void logError(String message, String tokenPreview, Exception ex) {
        String safeToken = tokenPreview.length() > 10
                ? tokenPreview.substring(0, 10) + "..."
                : tokenPreview;
        System.err.println("[" + java.time.LocalDateTime.now() + "] " + message + " | Token: " + safeToken);
        ex.printStackTrace();
    }

    @GetMapping("/sat")
    public ResponseEntity<List<?>> login_sat(@RequestParam String serie) {
//        System.out.println("login no SAT");

        TokenSatDTO token = apiSAT.autenticaSatService();

        List<SatEquipamentoDTO> dd = apiSAT.verificaSerieSatService(token, serie);
//        System.out.println("dd: "+dd);
        return ResponseEntity.ok(dd);


    }


}
