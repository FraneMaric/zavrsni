package hr.spring.zavrsni.services;

import java.util.List;

import org.springframework.stereotype.Component;

import hr.spring.zavrsni.models.Mail;

@Component
public interface MailService {
    public Mail saveMail(Mail mail);
    public Mail findById(Long id);
    public List<Mail> findAllById(String id);
    public List<Mail> findAllByRecever(String username);
    public List<Mail> findAllBySender(String username);
    public void delete(long id);
    public List<Mail> findAllBySearch(String search);
    public List<Mail> findAllByNameAndSurname(String user);
    public List<Mail> findAllByNameAndSurname2(String user);
    public List<Mail> findAllByNameAndSurname3(String user);
    //public List<Mail> findAllByName(String user);
}
