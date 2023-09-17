package hr.spring.zavrsni.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSendService {

    @Autowired
    private JavaMailSender mailSender;


    public void sendEmail(String username, String body, String stae){
        SimpleMailMessage message =new SimpleMailMessage();
         
        message.setFrom("zavrsni257@gmail.com");
        message.setTo(username);
        message.setText(body);
        message.setSubject(stae);

        mailSender.send(message);
    }
}
