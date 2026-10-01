package com.devsenai2a.scomptec.controller;

import com.devsenai2a.scomptec.model.LeituraMaquina;
import com.devsenai2a.scomptec.model.Maquina;
import com.devsenai2a.scomptec.repository.LeituraMaquinaRepository;
import com.devsenai2a.scomptec.repository.MaquinaRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/leituras")
@CrossOrigin(origins = "*")
public class LeituraMaquinaController {

    private final LeituraMaquinaRepository leituraRepository;
    private final MaquinaRepository maquinaRepository;

    public LeituraMaquinaController(
            LeituraMaquinaRepository leituraRepository,
            MaquinaRepository maquinaRepository) {

        this.leituraRepository = leituraRepository;
        this.maquinaRepository = maquinaRepository;
    }

    @PostMapping("/maquina/{maquinaId}")
    public ResponseEntity<?> registrar(
            @PathVariable Long maquinaId,
            @RequestBody LeituraMaquina leitura) {

        Maquina maquina = maquinaRepository
                .findById(maquinaId)
                .orElse(null);

        if (maquina == null) {
            return ResponseEntity.notFound().build();
        }

        leitura.setMaquina(maquina);

        if (leitura.getDataHora() == null) {
            leitura.setDataHora(LocalDateTime.now());
        }

        return ResponseEntity.ok(
                leituraRepository.save(leitura)
        );
    }

    @GetMapping("/maquina/{maquinaId}")
    public ResponseEntity<?> listar(
            @PathVariable Long maquinaId) {

        if (!maquinaRepository.existsById(maquinaId)) {
            return ResponseEntity.notFound().build();
        }

        List<LeituraMaquina> leituras =
                leituraRepository
                        .findByMaquinaIdOrderByDataHoraDesc(maquinaId);

        return ResponseEntity.ok(leituras);
    }
}