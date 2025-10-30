package com.chamados.Per2park.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")

@Data
@ToString
public class User {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Id
    @Column(name = "chamado", nullable = false, unique = true)
    private Long chamado; // ← chave primária vinda do front

    private String nome;

    private String email;

    private Long status;

    @CreationTimestamp
    private LocalDateTime dataCriacao; // ← data e hora automáticas

}
