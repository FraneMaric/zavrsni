package hr.spring.zavrsni.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hr.spring.zavrsni.models.Korisnik;




@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Korisnik>{
    Korisnik findByUserName(String userName);
    Korisnik findById(long id);

    @Query(value = "SELECT *FROM korisnik where type = 'postar'",nativeQuery = true)
    List<Korisnik> findAllSender();

    @Query(value = "SELECT *FROM korisnik where type = 'user'",nativeQuery = true)
    List<Korisnik> findAllUser();
}
