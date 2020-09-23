package cz.tkacikd.consumerapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender javaMailSender;
    private SimpleMailMessage emailTemplate;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, SimpleMailMessage emailTemplate){
        this.javaMailSender = javaMailSender;
        this.emailTemplate = emailTemplate;
    }


    public void sendMailToCustomer() {
        SimpleMailMessage msg = new SimpleMailMessage(emailTemplate);
        javaMailSender.send(msg);
    }
}
