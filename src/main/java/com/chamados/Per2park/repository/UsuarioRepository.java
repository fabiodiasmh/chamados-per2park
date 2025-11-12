package com.chamados.Per2park.repository;

import com.chamados.Per2park.entity.Chamado;
import com.chamados.Per2park.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


}
