package com.chamados.Per2park.controller.ResponseDTO;

import lombok.Data;


public record UsuarioPertoDTO(
//        Long id,
        String nome,
//        String login,
        String email,
        String cargo,
        String whatsapp
) {

}
