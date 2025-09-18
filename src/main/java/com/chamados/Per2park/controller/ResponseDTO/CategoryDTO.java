package com.chamados.Per2park.controller.ResponseDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryDTO {
    @JsonProperty("Id")
    private String id;

    @JsonProperty("Name")
    private String Name;
}
