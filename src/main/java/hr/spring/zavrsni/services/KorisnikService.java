package hr.spring.zavrsni.services;

import java.util.List;

import org.springframework.stereotype.Component;

import hr.spring.zavrsni.models.Korisnik;

@Component
public interface KorisnikService {
    Iterable<Korisnik> getAllKorisnik();
    Iterable<Korisnik> getAllSender();
    Iterable<Korisnik> getAllUser();

    public Korisnik createKorisnik(Korisnik korisnik);
    public Korisnik findKorisnikbyUsername(String userName);
    public Korisnik findKorisnikById(Long id);
    public boolean userNameTaken(String username);
    public void editKorisnik(Korisnik korisnik);
    public void deleteKorisnik(Korisnik korisnik);
    public List<String> getAllUsernames();
}