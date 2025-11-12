package com.chamados.Per2park.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario_logado")

@Data
@ToString
public class UserLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ← chave primária gerada automaticamente (auto-incremento)
    private Long id;

    private Long usuario_id;

    private String nome;

    private String email;

    private String ip_origem;

    @Column(columnDefinition = "TEXT")
    private String user_agent;

    @CreationTimestamp
    private LocalDateTime data_acesso; // ← data e hora automáticas
}
