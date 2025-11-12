CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    usuario_id VARCHAR(100),
    nome VARCHAR(150),
    email VARCHAR(150),
    cargo VARCHAR(100),
    whatsapp VARCHAR(20),
    ativo BOOLEAN DEFAULT true,
    data_criacao TIMESTAMP DEFAULT NOW()
);

CREATE TABLE chamados (
    id SERIAL PRIMARY KEY,
    status VARCHAR(50),
    data_abertura TIMESTAMP DEFAULT NOW(),
    usuario_id BIGINT REFERENCES usuarios(id)
);