package hr.spring.zavrsni.services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.spring.zavrsni.models.FileModel;
import hr.spring.zavrsni.repository.FileRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;
    
    @Override
    public FileModel saveFile(FileModel file){
        return fileRepository.save(file);
    }
    

    @Override
    public List<FileModel> findAllByRecever(String username){
        return fileRepository.findAllByRecever(username);
    }

    @Override
    public List<FileModel> findAllBySender(String username){
        return fileRepository.findAllBySender(username);
    }

    @Override
    public FileModel findById(Long id){
        return fileRepository.findById(id);
    }

    @Override
    public void delete(Long id){
        FileModel file=fileRepository.findById(id);
        fileRepository.delete(file);
    }
}
