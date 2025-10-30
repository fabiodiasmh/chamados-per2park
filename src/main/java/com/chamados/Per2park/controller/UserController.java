package com.chamados.Per2park.controller;

import com.chamados.Per2park.controller.RequestDTO.AssistanceCallResponseDTO;
import com.chamados.Per2park.entity.User;
import com.chamados.Per2park.service.ApiService;
import com.chamados.Per2park.service.UsuarioService;
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

}
