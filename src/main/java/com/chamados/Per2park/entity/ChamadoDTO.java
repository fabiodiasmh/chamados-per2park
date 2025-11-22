package com.chamados.Per2park.entity;

import com.chamados.Per2park.controller.RequestDTO.StatusDTO;
import lombok.Data;

import java.time.LocalDateTime;

public record ChamadoDTO(
        Long chamadoId,
//        String email,
        Long statusId,     // ← só o ID (o nome vem do banco)
        LocalDateTime dataCriacao,
        Long usuarioId,        // ← só o ID (seguro e suficiente para front)
        String nomeUsuario     // ← opcional, mas útil para exibição
) {}