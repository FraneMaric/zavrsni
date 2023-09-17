package hr.spring.zavrsni.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String type;

    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    private String userName;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    private String password;

    private String oib;

    private String ime;
    
    private String prezime;

    private boolean potvrdio;

    public boolean isPotvrdio() {
        return potvrdio;
    }
    public void setPotvrdio(boolean potvrdio) {
        this.potvrdio = potvrdio;
    }
    public String getOib() {
        return oib;
    }
    public void setOib(String oib) {
        this.oib = oib;
    }
    public Korisnik() {
    }
    public Korisnik( String userName,String ime,String prezime, String password,String OIB) {
        this.userName = userName;
        this.ime=ime;
        this.prezime=prezime;
        this.password = password;
        this.oib=OIB;
    }
    public String getIme() {
        return ime;
    }
    public void setIme(String ime) {
        this.ime = ime;
    }
    public String getPrezime() {
        return prezime;
    }
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
}
