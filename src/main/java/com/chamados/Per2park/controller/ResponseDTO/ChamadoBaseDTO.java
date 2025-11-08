package com.chamados.Per2park.controller.ResponseDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

    @Data
    public class ChamadoBaseDTO {

        @JsonProperty("Id")
        private Long id;

        @JsonProperty("OpeningDate")
        private LocalDateTime openingDate;

        @JsonProperty("Description")
        private String description;

        @JsonProperty("Status")
        private Integer status; // <-- usaremos esse para agrupar

        @JsonProperty("Priority")
        private Integer priority;

        @JsonProperty("User")
        private UserDTO user;

        @JsonProperty("Category")
        private CategoryDTO category;

        @JsonProperty("Local")
        private LocalsDTO local;

    }

