package com.chamados.Per2park.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

@Id
//@GeneratedValue(strategy = GenerationType.IDENTITY) // ← gera o ID automaticamente
@Column(name = "usuario_id", nullable = false, unique = true)
private Long id;

    private String nome;

    private String email;

    private String cargo;

    private String whatsapp;

    private Boolean ativo = true;

    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    // Um usuário tem vários chamados
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    private List<Chamado> chamados;
}