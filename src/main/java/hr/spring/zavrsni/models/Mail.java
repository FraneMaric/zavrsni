package hr.spring.zavrsni.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table
public class Mail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    private String sender;
    private String recever;
    private String title;
    private String message;
    private long fileId;
    private String fileName;
    private String vrijeme; 
    private String receverIme;
    private String receverPrezime;

    public String getReceverIme() {
        return receverIme;
    }
    public void setReceverIme(String receverIme) {
        this.receverIme = receverIme;
    }
    public String getReceverPrezime() {
        return receverPrezime;
    }
    public void setReceverPrezime(String receverPrezime) {
        this.receverPrezime = receverPrezime;
    }
    public String getVrijeme() {
        return vrijeme;
    }
    public void setVrijeme(String vrijeme) {
        this.vrijeme = vrijeme;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getRecever() {
        return recever;
    }
    public void setRecever(String recever) {
        this.recever = recever;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public long getFileId() {
        return fileId;
    }
    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

}
