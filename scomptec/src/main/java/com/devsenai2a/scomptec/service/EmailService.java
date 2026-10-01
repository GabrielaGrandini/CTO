package com.devsenai2a.scomptec.service;

import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final Resend resend;

    public EmailService() {

        this.resend = new Resend(System.getenv("MAIL_PASSWORD_KEY"));

    }

    public void enviarEmail(String destinatario, String assunto, String mensagem) {

        CreateEmailOptions params = CreateEmailOptions.builder()

                .from("onboarding@resend.dev")

                .to(destinatario)

                .subject(assunto)

                .text(mensagem)

                .build();

        try {

            resend.emails().send(params);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}
