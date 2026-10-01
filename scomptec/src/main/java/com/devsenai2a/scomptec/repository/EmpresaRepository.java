package com.devsenai2a.scomptec.repository;

import com.devsenai2a.scomptec.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

}