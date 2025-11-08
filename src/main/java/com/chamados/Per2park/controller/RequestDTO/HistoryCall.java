package com.chamados.Per2park.controller.RequestDTO;

import com.chamados.Per2park.controller.ResponseDTO.UserDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

    public class HistoryCall {

    @JsonProperty("Id")
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("Date")
    private LocalDateTime date;

    @JsonProperty("Reason")
    private Object reason;

    @JsonProperty("User")
    private UserDTO user;

    @JsonProperty("Priority")
    private Integer priority;

    @JsonProperty("Status")
    private Integer status;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("DescriptionUser")
    private String DescriptionUser;


    // getters e setters
}
