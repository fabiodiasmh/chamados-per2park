-- 1. Remover PK antiga baseada em chamado_id
ALTER TABLE chamados
DROP CONSTRAINT IF EXISTS chamados_pkey;

-- 2. Criar nova coluna ID autoincrement (BIGSERIAL)
ALTER TABLE chamados
ADD COLUMN id BIGSERIAL;

-- 3. Definir id como nova chave primária
ALTER TABLE chamados
ADD CONSTRAINT chamados_pkey PRIMARY KEY (id);

-- 4. Remover UNIQUE ou restrições desnecessárias do chamado_id
ALTER TABLE chamados
DROP CONSTRAINT IF EXISTS chamado_id_key;

ALTER TABLE chamados
ALTER COLUMN chamado_id DROP NOT NULL;
