package hr.spring.zavrsni.services;

import hr.spring.zavrsni.models.PotvrdioModel;

public interface PotvrdioService {
    public PotvrdioModel saveConfirm(PotvrdioModel potvrdioModel);
    public void deleteConfirm(long id);
    public PotvrdioModel findByKod(int kod);
}
