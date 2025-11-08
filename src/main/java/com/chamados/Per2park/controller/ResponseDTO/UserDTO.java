package com.chamados.Per2park.controller.ResponseDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class UserDTO {

    @JsonProperty("Id")
    private Long id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Affiliated")
    private Object Affiliated;

    @JsonProperty("Client")
    private Object Client;

    @JsonProperty("Group")
    private Object Group;

    @JsonProperty("IndActive")
    private Integer IndActive;

//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("LastLogin")
    private LocalDate LastLogin;

    @JsonProperty("Locations")
    private Object Locations;

    @JsonProperty("Login")
    private Object Login;

    @JsonProperty("Password")
    private Object Password;

    @JsonProperty("Token")
    private Object Token;

    @JsonProperty("UserType")
    private Object UserType;

//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("Validate")
    private LocalDate Validate;
}
