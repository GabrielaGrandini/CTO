package com.devsenai2a.scomptec.controller;

import com.devsenai2a.scomptec.model.Usuario;
import com.devsenai2a.scomptec.repository.UsuarioRepository;
import com.devsenai2a.scomptec.model.TokenRecuperacao;
import com.devsenai2a.scomptec.repository.TokenRecuperacaoRepository;
import com.devsenai2a.scomptec.service.EmailService;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpSession;
import com.devsenai2a.scomptec.service.UsuarioSessaoService;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioRepository repository;
    private final TokenRecuperacaoRepository tokenRepository;
    private final EmailService emailService;
    private final UsuarioSessaoService usuarioSessaoService;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public UsuarioController(
            UsuarioRepository repository,
            TokenRecuperacaoRepository tokenRepository,
            EmailService emailService,
            UsuarioSessaoService usuarioSessaoService) {

        this.repository = repository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
        this.usuarioSessaoService = usuarioSessaoService;
    }

    @PostMapping
    public ResponseEntity<?> criar(
            @RequestBody Usuario usuario,
            HttpSession session) {

        Usuario usuarioLogado =
                usuarioSessaoService.getUsuarioLogado(session);

        if (usuarioLogado == null) {
            return ResponseEntity.status(401)
                    .body("Não autenticado.");
        }

        if (!"SCOMPTEC".equals(usuarioLogado.getTipo())) {
            return ResponseEntity.status(403)
                    .body("Apenas usuários SCOMPTEC podem cadastrar usuários.");
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        Usuario usuarioCriado = repository.save(usuario);

        return ResponseEntity.ok(new UsuarioResponse(usuarioCriado));
    }

    @GetMapping
    public ResponseEntity<?> listar(HttpSession session) {

        Usuario usuarioLogado =
                usuarioSessaoService.getUsuarioLogado(session);

        if (usuarioLogado == null) {
            return ResponseEntity.status(401)
                    .body("Não autenticado.");
        }

        if (!"SCOMPTEC".equals(usuarioLogado.getTipo())) {
            return ResponseEntity.status(403)
                    .body("Acesso não permitido.");
        }

        return ResponseEntity.ok(
                repository.findAll()
                        .stream()
                        .map(UsuarioResponse::new)
                        .toList()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioResponse> login(
            @RequestBody LoginRequest dados,
            HttpSession session) {

        Usuario usuario = repository.findByEmail(dados.getEmail())
                .orElse(null);

        if (usuario == null) {
            return ResponseEntity.status(401).build();
        }

        if (!passwordEncoder.matches(dados.getSenha(), usuario.getSenha())) {
            return ResponseEntity.status(401).build();
        }

        session.setAttribute("usuarioId", usuario.getId());

        return ResponseEntity.ok(new UsuarioResponse(usuario));
    }

    @PostMapping("/recuperar-senha")
    public String recuperarSenha(@RequestBody RecuperarSenhaRequest dados) {

        Usuario usuario = repository.findByEmail(dados.getEmail())
                .orElse(null);

        if (usuario == null) {
            return "Se o e-mail estiver cadastrado, você receberá as instruções de recuperação.";
        }

        String token = UUID.randomUUID().toString();

        TokenRecuperacao recuperacao = new TokenRecuperacao();

        recuperacao.setToken(token);
        recuperacao.setUsuario(usuario);
        recuperacao.setExpiracao(LocalDateTime.now().plusMinutes(30));

        tokenRepository.save(recuperacao);

        String link = "http://127.0.0.1:5500/projeto/redefinir-senha.html?token=" + token;

        String mensagem = """
                Olá!

                Recebemos uma solicitação para redefinir a senha da sua conta SCOMPTEC.

                Acesse o link abaixo para criar uma nova senha:

                %s

                Este link é válido por 30 minutos.

                Se você não solicitou a recuperação de senha, ignore este e-mail.

                Atenciosamente,
                Equipe SCOMPTEC
                """.formatted(link);

        emailService.enviarEmail(
                usuario.getEmail(),
                "Recuperação de senha - SCOMPTEC",
                mensagem
        );

        return "Se o e-mail estiver cadastrado, você receberá as instruções de recuperação.";
    }
    
    @PostMapping("/redefinir-senha")
    public String redefinirSenha(@RequestBody RedefinirSenhaRequest dados) {

        TokenRecuperacao recuperacao = tokenRepository
                .findByToken(dados.getToken())
                .orElse(null);

        if (recuperacao == null) {
            return "Token inválido.";
        }

        if (recuperacao.getExpiracao().isBefore(LocalDateTime.now())) {
            return "Token expirado.";
        }

        Usuario usuario = recuperacao.getUsuario();

        usuario.setSenha(passwordEncoder.encode(dados.getNovaSenha()));

        repository.save(usuario);

        tokenRepository.delete(recuperacao);

        return "Senha redefinida com sucesso.";
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {

        session.invalidate();

        return ResponseEntity.ok("Logout realizado com sucesso.");
    }
}