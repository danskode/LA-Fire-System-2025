package org.kea.nicolainielsen.fire;

import java.util.List;

public interface FireService {

    List<FireModel> findAllFires();

    FireModel getFireModelbyID(Integer id);

    void save(FireModel fireModel1);

    void delete(FireModel fireModel1);

}
