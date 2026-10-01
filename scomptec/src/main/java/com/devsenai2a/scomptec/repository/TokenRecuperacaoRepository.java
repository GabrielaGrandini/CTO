package com.devsenai2a.scomptec.repository;

import com.devsenai2a.scomptec.model.TokenRecuperacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRecuperacaoRepository
        extends JpaRepository<TokenRecuperacao, Long> {

    Optional<TokenRecuperacao> findByToken(String token);
}