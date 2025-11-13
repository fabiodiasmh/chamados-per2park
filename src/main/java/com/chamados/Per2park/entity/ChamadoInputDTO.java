package com.chamados.Per2park.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChamadoInputDTO {
    @NotNull
    private Long chamadoId;

    @JsonProperty("status")
    @NotNull(message = "O campo 'status' é obrigatório")
    private Long statusId;

    @Email
    private String email;

    @NotNull
    private UsuarioInput usuario;

    @Data
    public static class UsuarioInput {
        @NotNull
        private Long id;
    }
}