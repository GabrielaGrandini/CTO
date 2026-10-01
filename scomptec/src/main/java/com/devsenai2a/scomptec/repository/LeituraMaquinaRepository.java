package com.devsenai2a.scomptec.repository;

import com.devsenai2a.scomptec.model.LeituraMaquina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeituraMaquinaRepository
        extends JpaRepository<LeituraMaquina, Long> {

    List<LeituraMaquina> findByMaquinaIdOrderByDataHoraDesc(Long maquinaId);
}