package com.chamados.Per2park.controller;

import com.chamados.Per2park.controller.RequestDTO.RequestAutentica;
import com.chamados.Per2park.controller.ResponseDTO.ChamadoBaseDTO;
import com.chamados.Per2park.controller.ResponseDTO.ChamadosAgrupadosPorStatusDTO;
import com.chamados.Per2park.controller.ResponseDTO.TokenDTO;
import com.chamados.Per2park.service.ApiService;
import com.chamados.Per2park.service.SeparaStatusChamados;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ChamadosController {

    private final ApiService apiService;
    private final SeparaStatusChamados separaStatusChamados;



    public ChamadosController(ApiService apiService, SeparaStatusChamados separaStatusChamados) {
        this.apiService = apiService;

        this.separaStatusChamados = separaStatusChamados;
    }

    @PostMapping("/login")
    public void autentica(@RequestBody RequestAutentica dados, HttpSession session) {

        TokenDTO resp = apiService.ServiceAutentica(dados);
        // Salva o token na sessão
        session.setAttribute("TOKEN_USUARIO", resp.getToken());

//        return ResponseEntity.ok("ok");

    }

    @GetMapping("/chamados")
    public ResponseEntity<List<ChamadoBaseDTO>> RecebeRequestBody(HttpSession session) {
        String token = (String) session.getAttribute("TOKEN_USUARIO");
        List<ChamadoBaseDTO> chamados = apiService.ServiceGetChamados(token);
        return ResponseEntity.ok(chamados);
    }

    @GetMapping("/chamadosPorStatus")
    public ResponseEntity<Map<String,Long>> chamadosPorStatus(HttpSession session){
        String token = (String) session.getAttribute("TOKEN_USUARIO");
        Map<String, Long> a =  separaStatusChamados.getTotalQuantidadePorStatus(token);
        a.forEach((Nome,quantidade)-> System.out.println(Nome+": "+quantidade));


        return ResponseEntity.ok(a);

    }

    @GetMapping("/status")
    public ResponseEntity<List<ChamadoBaseDTO>> status(@RequestParam int valor, HttpSession session){
        String token = (String) session.getAttribute("TOKEN_USUARIO");
       List<ChamadoBaseDTO> da = separaStatusChamados.getChamadosPorStatus(token, valor);

        return ResponseEntity.ok(da);
    }

    @GetMapping("/locais")
    public ResponseEntity<List<Map.Entry<String,Long>>> locais(HttpSession session){
        String token = (String) session.getAttribute("TOKEN_USUARIO");

        List<Map.Entry<String,Long>> w = separaStatusChamados.getTop10Locais(token);

        w.forEach((s-> System.out.println(s)));

        return ResponseEntity.ok(w);
    }





}
