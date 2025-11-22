package com.chamados.Per2park.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserPgDTO {

    @JsonProperty("id")
    private Long id;


    @JsonProperty("nome")
    private String nome;


    @JsonProperty("email")
    private String email;

}
