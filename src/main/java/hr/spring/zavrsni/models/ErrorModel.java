package hr.spring.zavrsni.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ErrorModel {
    List<String> usernames;
    List<String> files;

    public ErrorModel(){
        this.usernames=new ArrayList<>();
        this.files=new ArrayList<>();
    }


    public List<String> getUsernames() {
        return usernames;
    }
    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }
    public List<String> getFiles() {
        return files;
    }
    public void setFiles(List<String> files) {
        this.files = files;
    }
    public void addUsername(String username){
        this.usernames.add(username);
    }
    public void addFile(String file){
        this.files.add(file);
    }
    public void removeDuplicateUsernames() {
        Set<String> set = new HashSet<>(this.usernames);
        this.usernames = new ArrayList<>(set);
    }
    // Method to remove duplicates from files
    public void removeDuplicateFiles() {
        Set<String> set = new HashSet<>(this.files);
        this.files = new ArrayList<>(set);
    }
}
