package com.chamados.Per2park.repository;

import com.chamados.Per2park.entity.StatusChamado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusChamadoRepository extends JpaRepository<StatusChamado, Long> {
    // Spring Data já oferece findAll(), findById(), etc.

}