package hr.spring.zavrsni.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.spring.zavrsni.models.FileModel;



@Repository
public interface FileRepository extends JpaRepository<FileModel,FileModel>{
    List<FileModel> findAllByRecever(String username);
    FileModel findById(Long id);
    List<FileModel> findAllBySender(String username);
}
