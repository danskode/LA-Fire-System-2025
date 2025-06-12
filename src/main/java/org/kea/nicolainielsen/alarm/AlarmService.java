package org.kea.nicolainielsen.alarm;

import java.util.List;

public interface AlarmService {

    public void save(AlarmModel alarmModel1);

    public AlarmModel findById(int id);

    public List<AlarmModel> findAll();

    void delete(AlarmModel alarm);

}
