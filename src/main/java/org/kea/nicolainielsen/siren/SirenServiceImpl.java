package org.kea.nicolainielsen.siren;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SirenServiceImpl implements SirenService {

    private SirenRepository sirenRepository;

    public SirenServiceImpl(SirenRepository sirenRepository) {
        this.sirenRepository = sirenRepository;
    }

    @Override
    public SirenModel getSireneModelbyID(Integer id) {
        if (id == null){
            return null;
        }
        Optional<SirenModel> optionalSireneModel = sirenRepository.findById(id);
        return optionalSireneModel.orElse(null);
    }

    @Override
    public void save(SirenModel sirenModel) {
        sirenRepository.save(sirenModel);
    }

    @Override
    public List<SirenModel> findAllSirens(){
        return sirenRepository.findAll();
    }
}
