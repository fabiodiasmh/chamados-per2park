package com.chamados.Per2park.repository;

import com.chamados.Per2park.entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioLogadoRepository extends JpaRepository<UserLogin, Long> {
}
