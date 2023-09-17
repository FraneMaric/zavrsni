package hr.spring.zavrsni.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hr.spring.zavrsni.models.Korisnik;
import hr.spring.zavrsni.repository.KorisnikRepository;

@Service
@Transactional
public class KorisnikServiceImpl implements KorisnikService{

    @Autowired
    private KorisnikRepository korisnikRepository;
    
    @Override
    public Iterable<Korisnik> getAllKorisnik() {
        return this.korisnikRepository.findAll();
    }

    @Override
    public Korisnik createKorisnik(Korisnik korisnik){
        return korisnikRepository.save(korisnik);
    }

    @Override
    public Korisnik findKorisnikbyUsername(String userName){
        return korisnikRepository.findByUserName(userName);
    }

    @Override
    public Korisnik findKorisnikById(Long id){
        return korisnikRepository.findById(id);
    }

    @Override
    public boolean userNameTaken(String username){
        if(korisnikRepository.findByUserName(username)==null){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void editKorisnik(Korisnik korisnik){
        Korisnik korisnik2=korisnikRepository.findById(korisnik.getId());
        korisnik2.setUserName(korisnik.getUserName());
        korisnikRepository.save(korisnik2);
    }

    @Override
    public void deleteKorisnik(Korisnik korisnik){
        korisnikRepository.delete(korisnik);
    }

    @Override
    public Iterable<Korisnik> getAllSender(){
        return korisnikRepository.findAllSender();
    }

    @Override
    public Iterable<Korisnik> getAllUser(){
        return korisnikRepository.findAllUser();
    }
}
