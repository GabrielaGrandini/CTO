package com.devsenai2a.scomptec.controller;

import com.devsenai2a.scomptec.model.Usuario;
import com.devsenai2a.scomptec.model.Empresa;

public class UsuarioResponse {

    private Long id;
    private String nome;
    private String email;
    private String tipo;
    private Empresa empresa;

    public UsuarioResponse(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.tipo = usuario.getTipo();
        this.empresa = usuario.getEmpresa();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTipo() {
        return tipo;
    }

    public Empresa getEmpresa() {
        return empresa;
    }
}