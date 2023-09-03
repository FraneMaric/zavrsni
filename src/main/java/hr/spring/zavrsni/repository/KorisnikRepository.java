package hr.spring.zavrsni.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.spring.zavrsni.models.Korisnik;




@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Korisnik>{
    Korisnik findByUserName(String userName);
    Korisnik findById(long id);
}
