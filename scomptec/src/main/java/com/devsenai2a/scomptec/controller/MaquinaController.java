package com.devsenai2a.scomptec.controller;

import com.devsenai2a.scomptec.model.Maquina;
import com.devsenai2a.scomptec.model.Usuario;
import com.devsenai2a.scomptec.repository.MaquinaRepository;
import com.devsenai2a.scomptec.service.UsuarioSessaoService;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/maquinas")
@CrossOrigin(origins = "*")
public class MaquinaController {

    private final MaquinaRepository repository;
    private final UsuarioSessaoService usuarioSessaoService;

    public MaquinaController(
            MaquinaRepository repository,
            UsuarioSessaoService usuarioSessaoService) {

        this.repository = repository;
        this.usuarioSessaoService = usuarioSessaoService;
    }

    @PostMapping
    public ResponseEntity<?> criar(
            @RequestBody Maquina maquina,
            HttpSession session) {

        Usuario usuario = usuarioSessaoService.getUsuarioLogado(session);

        if (usuario == null) {
            return ResponseEntity.status(401)
                    .body("Não autenticado.");
        }

        if (!"SCOMPTEC".equals(usuario.getTipo())) {
            return ResponseEntity.status(403)
                    .body("Apenas usuários SCOMPTEC podem cadastrar máquinas.");
        }

        return ResponseEntity.ok(repository.save(maquina));
    }

    @GetMapping
    public ResponseEntity<?> listar(HttpSession session) {

        Usuario usuario = usuarioSessaoService.getUsuarioLogado(session);

        if (usuario == null) {
            return ResponseEntity.status(401)
                    .body("Não autenticado.");
        }

        if ("SCOMPTEC".equals(usuario.getTipo())) {
            return ResponseEntity.ok(repository.findAll());
        }

        if ("CLIENTE".equals(usuario.getTipo())) {
            return ResponseEntity.ok(
                    repository.findByEmpresaId(usuario.getEmpresa().getId())
            );
        }

        return ResponseEntity.status(403)
                .body("Acesso não permitido.");
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<?> listarPorEmpresa(
            @PathVariable Long empresaId,
            HttpSession session) {

        Usuario usuario = usuarioSessaoService.getUsuarioLogado(session);

        if (usuario == null) {
            return ResponseEntity.status(401)
                    .body("Não autenticado.");
        }

        if ("SCOMPTEC".equals(usuario.getTipo())) {
            return ResponseEntity.ok(
                    repository.findByEmpresaId(empresaId)
            );
        }

        if ("CLIENTE".equals(usuario.getTipo())) {

            if (usuario.getEmpresa() == null ||
                !usuario.getEmpresa().getId().equals(empresaId)) {

                return ResponseEntity.status(403)
                        .body("Acesso não permitido.");
            }

            return ResponseEntity.ok(
                    repository.findByEmpresaId(empresaId)
            );
        }

        return ResponseEntity.status(403)
                .body("Acesso não permitido.");
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(
            @PathVariable Long id,
            HttpSession session) {

        Usuario usuario = usuarioSessaoService.getUsuarioLogado(session);

        if (usuario == null) {
            return ResponseEntity.status(401)
                    .body("Não autenticado.");
        }

        Maquina maquina = repository.findById(id).orElse(null);

        if (maquina == null) {
            return ResponseEntity.status(404)
                    .body("Máquina não encontrada.");
        }

        if ("SCOMPTEC".equals(usuario.getTipo())) {
            return ResponseEntity.ok(maquina);
        }

        if ("CLIENTE".equals(usuario.getTipo())) {

            if (usuario.getEmpresa() == null ||
                maquina.getEmpresa() == null ||
                !usuario.getEmpresa().getId().equals(maquina.getEmpresa().getId())) {

                return ResponseEntity.status(403)
                        .body("Acesso não permitido.");
            }

            return ResponseEntity.ok(maquina);
        }

        return ResponseEntity.status(403)
                .body("Acesso não permitido.");
    }
}