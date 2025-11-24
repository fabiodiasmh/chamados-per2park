package com.chamados.Per2park.controller.ResponseDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class TokenDTO {
    @JsonProperty("token")
    private String token;

    @JsonProperty("User")
    private JsonNode user;

    @JsonProperty("dadosUsuario")
    private UsuarioPertoDTO dadosUsuario; // << antes era Usuario (entidade)
}
