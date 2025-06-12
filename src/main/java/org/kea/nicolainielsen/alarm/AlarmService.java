package org.kea.nicolainielsen.alarm;

import java.util.List;
import java.util.Optional;

public interface AlarmService {

    AlarmModel findById(int id);

    List<AlarmModel> findAll();


}
