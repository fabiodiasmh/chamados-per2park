INSERT INTO status_chamado (status_id, name) VALUES
  (0, 'Aberto'),
  (1, 'Em Análise'),
  (2, 'Em Atendimento'),
  (5, 'Fechado'),
  (6, 'Reaberto'),
  (8, 'Aguardando Feedback'),
  (9, 'Aguardando Resposta'),
  (10, 'Encaminhado para Nível 3'),
  (11, 'Aguardando Apoio Técnico'),
  (12, 'Encaminhado para Nível 2')
ON CONFLICT (status_id) DO NOTHING;