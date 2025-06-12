package org.kea.nicolainielsen.alarm;

import org.kea.nicolainielsen.siren.SirenModel;

import java.util.List;

public interface AlarmService {

    public void save(AlarmModel alarmModel1);

    public AlarmModel findById(int id);

    public List<AlarmModel> findAll();

    public void delete(AlarmModel alarm);

    public void createAlarmAndAssignNearbySirens(int fireId, double radiusKm);

    public List<AlarmModel> findByFireIdAndActiveTrue(int fireId);

    public void stopAlarmAndUpdateSiren(int alarmId);

}
