package com.chamados.Per2park.controller.ResponseDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SatEquipamentoDTO {


    @JsonProperty("nomeFantasia")
    private String nomeFantasia;
}
