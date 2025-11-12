package com.chamados.Per2park.repository;

import com.chamados.Per2park.entity.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

    List<Chamado> findByUsuarioId(Long id);

    Chamado findByChamadoId(Long chamadoId);


}
