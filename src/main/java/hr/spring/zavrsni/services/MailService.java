package hr.spring.zavrsni.services;

import java.util.List;

import org.springframework.stereotype.Component;

import hr.spring.zavrsni.models.Mail;

@Component
public interface MailService {
    public Mail saveMail(Mail mail);
    public Mail findById(Long id);
    public List<Mail> findAllByRecever(String username);
    public List<Mail> findAllBySender(String username);
    public void delete(long id);
}
