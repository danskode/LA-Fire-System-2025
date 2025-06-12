package org.kea.nicolainielsen.alarm;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AlarmServiceImpl implements AlarmService {

    private final AlarmRepository alarmRepository;
    public AlarmServiceImpl(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    @Override
    public AlarmModel findById(int id) {
        return alarmRepository.findById(id);
    }

    @Override
    public List<AlarmModel> findAll() {
        return alarmRepository.findAll();
    }

    public void save(AlarmModel alarmModel1) {
        alarmRepository.save(alarmModel1);
    }

    public void delete(AlarmModel alarmModel) {
        alarmRepository.delete(alarmModel);
    }
}
