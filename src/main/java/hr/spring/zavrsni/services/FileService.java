package hr.spring.zavrsni.services;




import java.util.List;

import org.springframework.stereotype.Component;


import hr.spring.zavrsni.models.FileModel;

@Component
public interface FileService {

    public FileModel saveFile(FileModel fileModel);
    public List<FileModel> findAllByRecever(String username);
    public List<FileModel> findAllBySender(String username);
    public FileModel findById(Long id);
    public void delete(Long id);
    
}
