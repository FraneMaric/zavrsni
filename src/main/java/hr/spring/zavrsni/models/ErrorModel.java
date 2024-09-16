package hr.spring.zavrsni.models;

public class ErrorModel {
    String fileName;
    String status;

    public ErrorModel(String fileName, String status) {
        this.fileName = fileName;
        this.status = status;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
