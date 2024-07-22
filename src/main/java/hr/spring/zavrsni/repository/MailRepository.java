package hr.spring.zavrsni.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hr.spring.zavrsni.models.Mail;

@Repository
public interface MailRepository extends JpaRepository<Mail,Mail> {
    List<Mail> findAllByRecever(String username);
    List<Mail> findAllBySender(String username);
    Mail findById(long id);
    @Query("SELECT m FROM Mail m WHERE LOWER (m.message) LIKE LOWER (CONCAT('%',:search, '%')) OR LOWER (m.sender) LIKE LOWER (CONCAT('%', :search, '%'))")
    List<Mail> findAllBySearch(@Param("search") String search);
    @Query("SELECT m FROM Mail m WHERE CONCAT(m.receverPrezime, ' ', m.receverIme) = :user")
    List<Mail> findAllByNameAndSurname(@Param("user") String user);

}
