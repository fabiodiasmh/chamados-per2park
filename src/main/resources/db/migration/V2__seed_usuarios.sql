INSERT INTO usuarios (usuario_id, nome, email, cargo, whatsapp, ativo, data_criacao) VALUES
  (1798, 'Fabio Dias', 'fabio.dias@perto.com.br', 'Analista de suporte', '559911111111', true, '2025-11-11 23:34:29.433'),
  (8899, 'GUILHERME ELOY', 'guilherme.eloy@wps-sa.com.br', 'Analista de suporte', '559911111111', true, '2025-11-11 23:35:42.315'),
  (10003, 'JANAINA MENDES', 'janaina.silva@wps-sa.com.br', 'Analista de suporte', '559911111111', true, '2025-11-11 23:36:20.000'),
  (8898, 'BRUNO DE SOUSA', 'bruno.sousa@wps-sa.com.br', 'Analista de suporte', '559911111111', true, '2025-11-11 23:36:53.714'),
  (9745, 'HENDERSON BERIGO', 'henderson.berigo@wps-sa.com.br', 'Analista de suporte', '559911111111', true, '2025-11-11 23:37:24.190'),
  (9120, 'VITOR MESSIAS', 'vitor.campos@wps-sa.com.br', 'Analista de suporte', '559911111111', true, '2025-11-11 23:37:57.778')
ON CONFLICT (usuario_id) DO NOTHING;