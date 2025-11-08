package com.chamados.Per2park.controller.ResponseDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LocalsDTO {

    @JsonProperty("Id")
    private Integer id;

    @JsonProperty("Name")
    private String Name;

    @JsonProperty("IndActive")
    private Integer IndActive;

    @JsonProperty("IndBlock")
    private Integer IndBlock;

    @JsonProperty("LocalParent")
    private Integer LocalParent;

    @JsonProperty("Number")
    private Integer Number;

    private Integer ParkingSpace;
}
