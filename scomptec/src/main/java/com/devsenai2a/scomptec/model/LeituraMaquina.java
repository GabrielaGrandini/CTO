package com.devsenai2a.scomptec.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class LeituraMaquina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double temperatura;
    private Double vibracao;
    private LocalDateTime dataHora;

    @ManyToOne
    private Maquina maquina;

    public LeituraMaquina() {}

    public Long getId() {
        return id;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public Double getVibracao() {
        return vibracao;
    }

    public void setVibracao(Double vibracao) {
        this.vibracao = vibracao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }
}