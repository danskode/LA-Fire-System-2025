package org.kea.nicolainielsen.fire;

import org.springframework.stereotype.Service;

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
    public FireModel save(FireModel fireModel1) {
        fireRepository.save(fireModel1);
        return fireModel1;
    }

    @Override
    public FireModel delete(FireModel fireModel1) {
        fireRepository.delete(fireModel1);
        return fireModel1;
    }
}
