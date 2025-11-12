-- ==============================
-- V2__insert_seed.sql
-- Dados iniciais para teste
-- ==============================

-- Inserindo usuários
INSERT INTO usuarios (nome, email, cargo, whatsapp, ativo)
VALUES
('Fabio Dias', 'fabio@empresa.com', 'Analista de Suporte', '(11)99999-0000', TRUE),
('João Silva', 'joao@empresa.com', 'Técnico de Campo', '(11)98888-1111', TRUE),
('Maria Souza', 'maria@empresa.com', 'Coordenadora de TI', '(11)97777-2222', TRUE);

-- Inserindo chamados relacionados aos usuários
INSERT INTO chamados (chamado, email, status, usuario_id)
VALUES
(1001, 'fabio@empresa.com', 1, 1),  -- Chamado do Fabio
(1002, 'joao@empresa.com', 2, 2),   -- Chamado do João
(1003, 'maria@empresa.com', 1, 3),  -- Chamado da Maria
(1004, 'fabio@empresa.com', 3, 1);  -- Segundo chamado do Fabio
