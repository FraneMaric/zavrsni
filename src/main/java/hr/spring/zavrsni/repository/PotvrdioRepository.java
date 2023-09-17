package hr.spring.zavrsni.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import hr.spring.zavrsni.models.PotvrdioModel;

@Repository
public interface PotvrdioRepository extends JpaRepository<PotvrdioModel,PotvrdioModel> {
    PotvrdioModel findById(long id);
    PotvrdioModel findByKod(int kod);
}
