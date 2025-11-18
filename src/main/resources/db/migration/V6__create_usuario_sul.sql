INSERT INTO usuarios (usuario_id, nome, email, cargo, whatsapp, ativo, data_criacao) VALUES
  (7691, 'FILIPE SOARES', 'filipe.soares@perto.com.br', 'Desenvolvedor de sistemas', '559911111111', true, '2025-11-11 23:34:29.433'),
  (898, 'ROBERSON DA SILVA ESSIG', 'roberson.silva@perto.com.br', 'Analista de suporte', '559911111111', true, '2025-11-11 23:35:42.315'),
  (10330, 'BARUERI', 'barueri@perto.com.br', 'Analista de suporte', '559911111111', true, '2025-11-11 23:36:20.000')
ON CONFLICT (usuario_id) DO NOTHING;