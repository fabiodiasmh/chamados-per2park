package com.chamados.Per2park.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "chamados")

@Data
@ToString(exclude = {"usuario", "statusChamado"}) // evita loops

public class Chamado {


    // 🔹 Nova chave primária autogerada
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    // 🔹 Este campo agora DEIXA de ser PK e vira só um campo comum
    @Column(name = "chamado_id")
    private Long chamadoId;

    private String email;


//        private Long status;

    // ✅ Substitua 'Long status' por relacionamento
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
    private StatusChamado statusChamado;
    //✅ Agora o status é um objeto real — não um número mágico.


    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    // Muitos chamados pertencem a um único usuário
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    //    @JsonIgnore // ← evita serializar a lista de chamados ao serializar Usuario
    private Usuario usuario;

}
