package com.codeup.blog.services;

import com.codeup.blog.models.Post;
import org.springframework.beans.factory.annotation.*;
import org.springframework.mail.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("mailService")
public class EmailService {

    @Autowired
    public JavaMailSender emailSender;

    @Value("${spring.mail.from}")
    private String from;

    public void prepareAndSend(Post post, String subject, String body) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(post.getUser().getEmail());
        msg.setSubject(subject);
        msg.setText(body);
        new Thread(new RunnableEmail(this, msg)).start();
    }
}

class RunnableEmail implements Runnable {
    private EmailService emailSvc;
    private SimpleMailMessage msg;

    public RunnableEmail(EmailService emailSvc, SimpleMailMessage msg) {
        this.emailSvc = emailSvc;
        this.msg = msg;
    }

    @Override
    public void run() {
        try {
            emailSvc.emailSender.send(msg);
        } catch(MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }
}
