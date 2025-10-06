package com.ebac.plataforma_streaming.repository;

import com.ebac.plataforma_streaming.entities.UsuarioAssinante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioAssinanteRepository extends JpaRepository<UsuarioAssinante, Integer> {
    UsuarioAssinante findByIdUsuario(Integer idUsuario);
}
