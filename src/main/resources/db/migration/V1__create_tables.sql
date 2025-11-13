-- 1. Tabela independente
CREATE TABLE status_chamado (
    status_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (status_id)
);

-- 2. Tabela independente
CREATE TABLE usuarios (
    usuario_id BIGINT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    cargo VARCHAR(255),
    whatsapp VARCHAR(20),
    ativo BOOLEAN NOT NULL DEFAULT true,
    data_criacao TIMESTAMP NOT NULL,
    PRIMARY KEY (usuario_id)
);

-- 3. Tabela dependente — SÓ CRIA A TABELA, NÃO INSERE
CREATE TABLE chamados (
    chamado_id BIGINT NOT NULL,
    email VARCHAR(255) NOT NULL,
    status_id BIGINT NOT NULL,
    data_criacao TIMESTAMP NOT NULL,
    usuario_id BIGINT NOT NULL,
    PRIMARY KEY (chamado_id),
    CONSTRAINT fk_chamado_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuarios (usuario_id),
    CONSTRAINT fk_chamado_status
        FOREIGN KEY (status_id) REFERENCES status_chamado (status_id)
);

-- Criar tabela de usuários logados
CREATE TABLE usuario_logado (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT,
    nome VARCHAR(255),
    email VARCHAR(255),
    ip_origem VARCHAR(100),
    user_agent TEXT,
    data_acesso TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);




