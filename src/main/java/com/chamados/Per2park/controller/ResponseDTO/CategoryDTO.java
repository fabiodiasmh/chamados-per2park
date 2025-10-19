package com.chamados.Per2park.controller.ResponseDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class CategoryDTO {
    @JsonProperty("Id")
    private String id;

    @JsonProperty("Name")
    private String Name;
}
