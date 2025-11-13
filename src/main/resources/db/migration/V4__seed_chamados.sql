INSERT INTO chamados (chamado_id, email, status_id, data_criacao, usuario_id) VALUES
  (834291, 'joao.silva@example.com', 1, CURRENT_TIMESTAMP, 1798),
  (592837, 'maria.santos@example.com', 2, CURRENT_TIMESTAMP, 1798),
  (173920, 'pedro.almeida@example.com', 2, CURRENT_TIMESTAMP, 8899),
  (948210, 'ana.oliveira@example.com', 5, CURRENT_TIMESTAMP, 10003),
  (761504, 'carla.souza@example.com', 0, CURRENT_TIMESTAMP, 9120)
ON CONFLICT (chamado_id) DO NOTHING;