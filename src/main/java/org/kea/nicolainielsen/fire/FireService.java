package org.kea.nicolainielsen.fire;

import java.util.List;

public interface FireService {

    List<FireModel> findAllFires();

    FireModel getFireModelbyID(Integer id);

    FireModel save(FireModel fireModel1);

    FireModel delete(FireModel fireModel1);

    List<FireModel> findActiveFires();
}
