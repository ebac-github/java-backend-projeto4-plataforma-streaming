package com.ebac.plataforma_streaming.repository;

import com.ebac.plataforma_streaming.entities.Usuario;
import com.ebac.plataforma_streaming.entities.UsuarioAssinante;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNome(String nome);

    long countByUsuarioAssinante(UsuarioAssinante usuarioAssinante);

    @Modifying
    @Transactional
    void deleteByUsuarioAssinante(UsuarioAssinante usuarioAssinante);

    @Modifying
    @Transactional
    void deleteByUsuarioAssinanteAndIdNot(UsuarioAssinante usuarioAssinante, Integer id);
}
