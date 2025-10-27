package com.chamados.Per2park.controller;

import com.chamados.Per2park.entity.User;
import com.chamados.Per2park.service.UsuarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UsuarioService usuarioService;

    public UserController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @GetMapping("/insert_usuario")
    public void tes(){
        User a = new User();
        a.setNome("Fabio Dias");
        a.setEmail("fabio@fabiodias");
usuarioService.salvar(a);

    }

}
