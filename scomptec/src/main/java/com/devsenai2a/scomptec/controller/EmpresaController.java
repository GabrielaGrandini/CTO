package com.devsenai2a.scomptec.controller;

import com.devsenai2a.scomptec.model.Empresa;
import com.devsenai2a.scomptec.repository.EmpresaRepository;
import org.springframework.web.bind.annotation.*;
import com.devsenai2a.scomptec.model.Usuario;
import com.devsenai2a.scomptec.service.UsuarioSessaoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

    private final EmpresaRepository repository;
    private final UsuarioSessaoService usuarioSessaoService;

    public EmpresaController(
            EmpresaRepository repository,
            UsuarioSessaoService usuarioSessaoService) {

        this.repository = repository;
        this.usuarioSessaoService = usuarioSessaoService;
    }

    @PostMapping
    public ResponseEntity<?> criar(
            @RequestBody Empresa empresa,
            HttpSession session) {

        Usuario usuario = usuarioSessaoService.getUsuarioLogado(session);

        if (usuario == null) {
            return ResponseEntity.status(401)
                    .body("Não autenticado.");
        }

        if (!"SCOMPTEC".equals(usuario.getTipo())) {
            return ResponseEntity.status(403)
                    .body("Apenas usuários SCOMPTEC podem cadastrar empresas.");
        }

        return ResponseEntity.ok(repository.save(empresa));
    }

    @GetMapping
    public ResponseEntity<?> listar(HttpSession session) {

        Usuario usuario = usuarioSessaoService.getUsuarioLogado(session);

        if (usuario == null) {
            return ResponseEntity.status(401)
                    .body("Não autenticado.");
        }

        if (!"SCOMPTEC".equals(usuario.getTipo())) {
            return ResponseEntity.status(403)
                    .body("Acesso não permitido.");
        }

        return ResponseEntity.ok(repository.findAll());
    }
}