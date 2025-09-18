package com.chamados.Per2park.controller.ResponseDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LocalsDTO {

    @JsonProperty("Id")
    private String id;

    @JsonProperty("Name")
    private String Name;
}
