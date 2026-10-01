package com.devsenai2a.scomptec.controller;

import com.devsenai2a.scomptec.service.EmailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "*")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/teste")
    public String testarEmail(@RequestParam String destinatario) {

        emailService.enviarEmail(
                destinatario,
                "Teste SCOMPTEC",
                "Este é um teste de envio de e-mail do sistema SCOMPTEC."
        );

        return "E-mail enviado com sucesso.";
    }
}