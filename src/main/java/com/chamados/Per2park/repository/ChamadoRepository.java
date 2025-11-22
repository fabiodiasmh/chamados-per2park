package com.chamados.Per2park.repository;

import com.chamados.Per2park.entity.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

    List<Chamado> findByUsuarioId(Long id);



    Chamado findByChamadoId(Long chamadoId);

    @Query("""
    SELECT c FROM Chamado c
    WHERE c.usuario.id = :usuarioId
    AND c.dataCriacao = (
        SELECT MAX(c2.dataCriacao)
        FROM Chamado c2
        WHERE c2.chamadoId = c.chamadoId
    )
    ORDER BY c.dataCriacao DESC
""")
    List<Chamado> findUltimosChamadosByUsuario(@Param("usuarioId") Long usuarioId);


}
