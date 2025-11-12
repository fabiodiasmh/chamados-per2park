CREATE TABLE usuarios (
    usuario_id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    cargo VARCHAR(100),
    whatsapp VARCHAR(20),
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE chamados (
    chamado BIGINT PRIMARY KEY,
    email VARCHAR(150),
    status BIGINT,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_id BIGINT NOT NULL,
    CONSTRAINT fk_chamado_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios (usuario_id)
        ON DELETE CASCADE
);
