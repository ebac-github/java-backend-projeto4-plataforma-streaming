package com.ebac.plataforma_streaming.repository;

import com.ebac.plataforma_streaming.entities.Filme;
import com.ebac.plataforma_streaming.enums.Plano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Integer> {
    List<Filme> findByPlanoMinimoIn(List<Plano> planosMinimos);
}
