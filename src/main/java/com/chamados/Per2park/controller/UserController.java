package com.chamados.Per2park.controller;

import com.chamados.Per2park.entity.Chamado;
import com.chamados.Per2park.entity.UserLogin;
import com.chamados.Per2park.entity.Usuario;
import com.chamados.Per2park.service.ApiService;
import com.chamados.Per2park.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UsuarioService usuarioService;
    private final ApiService apiService;

    public UserController(UsuarioService usuarioService, ApiService apiService) {
        this.usuarioService = usuarioService;
        this.apiService = apiService;
    }


    @PostMapping("/insert_chamado")
    public ResponseEntity<String> tes(@RequestBody Chamado dados){


    usuarioService.salva_chamado(dados);

        return  ResponseEntity.ok("Chamado inserido no banco");
    }

    @PostMapping("/user_login")
    public void userLogin( @RequestBody UserLogin dados, HttpServletRequest request){

        // Captura IP e User-Agent
        String ipOrigem = getClientIp(request);
        String userAgent = request.getHeader("User-Agent");

        UserLogin userlogin = new UserLogin();


        userlogin.setNome(dados.getNome());
        userlogin.setEmail(dados.getEmail());
        userlogin.setUsuario_id(dados.getUsuario_id()); // se existir
//        userlogin.setToken_acesso(dados.getToken_acesso()); // se existir
        userlogin.setIp_origem(ipOrigem);
        userlogin.setUser_agent(userAgent);

        usuarioService.salvarLogin(userlogin);
    }

    private String getClientIp(HttpServletRequest request) {
        // Verifica proxies/reverses
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isEmpty()) {
            return forwarded.split(",")[0];
        }
        return request.getRemoteAddr();
    }

    @PostMapping("/load_meus_chamados")
    public ResponseEntity<List<Chamado>> meus_chamados(@RequestBody Usuario dados){
        List<Chamado> chamados = usuarioService.busca_meus_chamados_service(dados);
        System.out.println(dados);
        System.out.println(dados);
        System.out.println(dados);
        System.out.println(dados);
        System.out.println(dados);
        System.out.println("Id do usuario recebido front: "+dados.getId());
        return ResponseEntity.ok(chamados);

    }

}
