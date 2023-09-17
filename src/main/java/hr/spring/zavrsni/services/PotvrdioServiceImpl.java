package hr.spring.zavrsni.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.spring.zavrsni.models.PotvrdioModel;
import hr.spring.zavrsni.repository.PotvrdioRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PotvrdioServiceImpl implements PotvrdioService {
    
    @Autowired
    private PotvrdioRepository potvrdioRepository;

    public PotvrdioModel saveConfirm(PotvrdioModel potvrdioModel){
        return potvrdioRepository.save(potvrdioModel);
    }

    public void deleteConfirm(long id){
        potvrdioRepository.delete(potvrdioRepository.findById(id));
    }

    public PotvrdioModel findByKod(int kod){
        return potvrdioRepository.findByKod(kod);
    }

    
}
