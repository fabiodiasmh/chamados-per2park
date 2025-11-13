package com.chamados.Per2park.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "status_chamado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusChamado {

    @Id
    @Column(name = "status_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    // Opcional: campos extras futuros
    // private String corHex;
    // private Integer ordem;
    // private Boolean ativo = true;
}