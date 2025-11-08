package com.chamados.Per2park.controller.RequestDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class UserSATDTO {

    @JsonProperty("codUsuario")
    private String codUsuario;

    @JsonProperty("senha")
    private String senha;

}
