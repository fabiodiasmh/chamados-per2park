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
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/api")
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

    @PostMapping("/login")
    public ResponseEntity<?> autentica(@RequestBody RequestAutentica dados, HttpSession session) {

        TokenDTO resp = apiService.ServiceAutentica(dados);

        session.setAttribute("TOKEN_USUARIO", resp.getToken());

        return ResponseEntity.ok(resp);

    }

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
////        a.forEach((Nome, quantidade) -> System.out.println(Nome + ": " + quantidade));
//
//        return ResponseEntity.ok(a);
//    }
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

        String token = extractAndValidateToken(authHeader);
        try {
            Boolean atualizado = apiService.UpdateChamadoService(token, dados);
            if (Boolean.TRUE.equals(atualizado)) {
                return ResponseEntity.ok("Chamado atualizado com sucesso");
            } else {
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
    public ResponseEntity<List<?>> login_sat(@RequestParam String serie){
//        System.out.println("login no SAT");

        TokenSatDTO token = apiSAT.autenticaSatService();

       List<SatEquipamentoDTO> dd = apiSAT.verificaSerieSatService(token,serie);
//        System.out.println("dd: "+dd);
        return ResponseEntity.ok(dd);


    }


}
