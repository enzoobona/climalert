package ar.edu.utn.ba.ddsi.climalert.services.impl;

import ar.edu.utn.ba.ddsi.climalert.services.AlertaService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class AlertaServiceImpl implements AlertaService {
    private final JavaMailSender mailSender;
    private List<String> emails;

    public AlertaServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
        this.emails = List.of(/*"admin@clima.com", "emergencias@clima.com", "meteorologia@clima.com",*/ "enzobona05@gmail.com");
    }

    @Override
    public boolean enviarAlerta(String mensaje) {
        this.emails.forEach(d -> enviarMail(d, mensaje));
        return true;
    }

    private boolean enviarMail(String direccion, String mensaje) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(direccion);
        email.setSubject("Climalert - Alerta del clima");
        email.setText(mensaje);
        email.setFrom("enzobonafinecontacto@gmail.com");
        mailSender.send(email);
        System.out.println("Mail enviado");
        return true;
    }

}