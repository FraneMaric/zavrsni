package hr.spring.zavrsni.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.spring.zavrsni.models.Mail;
import hr.spring.zavrsni.repository.MailRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class MailServiceImpl implements MailService {


    @Autowired
    private MailRepository mailRepository;

    public Mail saveMail(Mail mail){
        return mailRepository.save(mail);
    }

    public Mail findById(Long id){
        return mailRepository.findById(id);
    }

    public List<Mail> findAllById(String id){
        return mailRepository.findAllById(id);
    }

    public List<Mail> findAllByRecever(String username){
        return mailRepository.findAllByRecever(username);
    }

    public List<Mail> findAllBySender(String username){
        return mailRepository.findAllBySender(username);
    }

    public void delete(long id){
        mailRepository.delete(mailRepository.findById(id));
    }

    public List<Mail> findAllBySearch(String search){
        return mailRepository.findAllBySearch(search);
    }

    public List<Mail> findAllByNameAndSurname(String user){
        return mailRepository.findAllByNameAndSurname(user);
    }

    public List<Mail> findAllByNameAndSurname2(String user){
        return mailRepository.findAllByNameAndSurname2(user);
    }

    public List<Mail> findAllByNameAndSurname3(String user){
        return mailRepository.findAllByNameAndSurname3(user);
    }


    // public List<Mail> findAllByName(String user){
    //     return mailRepository.findAllByName(user);
    // }

}
