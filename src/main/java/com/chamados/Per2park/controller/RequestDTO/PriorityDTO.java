package com.chamados.Per2park.controller.RequestDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PriorityDTO {

    @JsonProperty("Id")
    private Integer Id;

    @JsonProperty("Description")
    private String Description;
}
