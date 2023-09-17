package hr.spring.zavrsni.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class PotvrdioModel {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    private String username;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    private int kod;
    public int getKod() {
        return kod;
    }
    public void setKod(int kod) {
        this.kod = kod;
    }
    
}
