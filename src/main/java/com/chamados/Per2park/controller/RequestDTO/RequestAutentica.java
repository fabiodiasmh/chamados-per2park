package com.chamados.Per2park.controller.RequestDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class RequestAutentica {

    @JsonProperty("Login")
    private String Login;

    @JsonProperty("Password")
    private String Password;

    @JsonProperty("Port")
    private Integer Port;


}
