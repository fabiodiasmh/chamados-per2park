package com.chamados.Per2park.controller.RequestDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StatusDTO {
    @JsonProperty("Id")
    private String Id;

    @JsonProperty("Name")
    private String Name;

}
