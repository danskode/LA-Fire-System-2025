package org.kea.nicolainielsen.fire;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class FireServiceImpl implements FireService {

    private FireRepository fireRepository;

    public FireServiceImpl(FireRepository fireRepository) {
        this.fireRepository = fireRepository;
    }

    @Override
    public List<FireModel> findAllFires() {
        return fireRepository.findAll();
    }

    @Override
    public FireModel getFireModelbyID(Integer id) {

        if (id == null){
            return null;
        }
        Optional<FireModel> optionalFireModel = fireRepository.findById(id);
        return optionalFireModel.orElse(null);
    }

    @Override
    public void save(FireModel fireModel1) {
        fireRepository.save(fireModel1);
    }

    @Override
    public void delete(FireModel fireModel1) {
        fireRepository.delete(fireModel1);
    }
}
