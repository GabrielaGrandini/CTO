package com.devsenai2a.scomptec.repository;

import com.devsenai2a.scomptec.model.Maquina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaquinaRepository extends JpaRepository<Maquina, Long> {

    List<Maquina> findByEmpresaId(Long empresaId);

}