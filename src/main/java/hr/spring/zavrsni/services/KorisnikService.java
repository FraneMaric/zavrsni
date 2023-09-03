package hr.spring.zavrsni.services;

import org.springframework.stereotype.Component;

import hr.spring.zavrsni.models.Korisnik;

@Component
public interface KorisnikService {
    Iterable<Korisnik> getAllKorisnik();

    public Korisnik createKorisnik(Korisnik korisnik);
    public Korisnik findKorisnikbyUsername(String userName);
    public Korisnik findKorisnikById(Long id);
    public boolean userNameTaken(String username);
    public void editKorisnik(Korisnik korisnik);
    public void deleteKorisnik(Korisnik korisnik);
}