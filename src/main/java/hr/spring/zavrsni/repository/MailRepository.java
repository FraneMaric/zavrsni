package hr.spring.zavrsni.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.spring.zavrsni.models.Mail;

@Repository
public interface MailRepository extends JpaRepository<Mail,Mail> {
    List<Mail> findAllByRecever(String username);
    List<Mail> findAllBySender(String username);
    Mail findById(long id);
    // List<Mail> findMail(String searchString);
}
