package com.devsenai2a.scomptec.service;

import com.devsenai2a.scomptec.model.Usuario;
import com.devsenai2a.scomptec.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

@Service
public class UsuarioSessaoService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioSessaoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario getUsuarioLogado(HttpSession session) {

        Object usuarioId = session.getAttribute("usuarioId");

        if (usuarioId == null) {
            return null;
        }

        Long id = (Long) usuarioId;

        return usuarioRepository.findById(id).orElse(null);
    }
}