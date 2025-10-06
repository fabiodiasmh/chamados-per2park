package com.chamados.Per2park.controller.RequestDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ReplicationQueueDTO {

    @JsonProperty("FirstRegister")
    private String FirstRegister;

    @JsonProperty("Pendencies")
    private Integer Pendencies;

    @JsonProperty("Errors")
    private Integer Errors;

}
