package org.kea.nicolainielsen.siren;

import java.util.List;

public interface SirenService {

    // Get one siren by id ...
    SirenModel getSireneModelbyID(Integer id);

    // Get all sirens ...
    List<SirenModel> findAllSirens();

    // Get all active sirens ...

    // Get all sirens within 10K from a fire by fire's id ...



    // Save a siren ...
    void save(SirenModel sirenModel);

}
