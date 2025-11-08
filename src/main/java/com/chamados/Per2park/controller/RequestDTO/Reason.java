package com.chamados.Per2park.controller.RequestDTO;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Reason {
        @JsonProperty("Id")
        private Long id;

        @JsonProperty("Description")
        private String description;

        @JsonProperty("IndActive")
        private Integer indActive;

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @JsonProperty("RegistryDate")
        private LocalDateTime registryDate;

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @JsonProperty("UpdateDate")
        private LocalDateTime updateDate;

        @JsonProperty("Type")
        private Object type;

        @JsonProperty("Client")
        private Object client;

        // getters e setters
    }

