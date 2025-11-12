package com.chamados.Per2park.controller;

import com.chamados.Per2park.controller.RequestDTO.AssistanceCallResponseDTO;
import com.chamados.Per2park.entity.User;
import com.chamados.Per2park.entity.UserLogin;
import com.chamados.Per2park.service.ApiService;
import com.chamados.Per2park.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UsuarioService usuarioService;
    private final ApiService apiService;

    public UserController(UsuarioService usuarioService, ApiService apiService) {
        this.usuarioService = usuarioService;
        this.apiService = apiService;
    }


    @PostMapping("/insert_usuario")
    public void tes(@RequestBody User dados){
//        User a = new User();
//        a.setNome("Fabio Dias");
//        a.setEmail("fabio@fabiodias");
//        a.setNome(dados.getNome());
//        a.setEmail(dados.getEmail());

usuarioService.salvar(dados);

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
        userlogin.setToken_acesso(dados.getToken_acesso()); // se existir
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

}
