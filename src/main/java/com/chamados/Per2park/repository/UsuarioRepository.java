package com.chamados.Per2park.repository;

import com.chamados.Per2park.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<User, Long> {
    // Métodos personalizados podem ser adicionados aqui
}
