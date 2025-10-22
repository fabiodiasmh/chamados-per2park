package com.chamados.Per2park.controller.ResponseDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TokenSatDTO {


    @JsonProperty("token")
    private String token;


}
