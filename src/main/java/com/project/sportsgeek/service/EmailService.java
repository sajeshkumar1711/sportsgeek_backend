package com.project.sportsgeek.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.project.sportsgeek.model.Email;

@Service
public class EmailService {
	@Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(Email email) throws  Exception {
        SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(email.getSetTo());
            msg.setSubject(email.getSetSubject());
            msg.setText(email.getMessage());
            javaMailSender.send(msg);
    }
}
