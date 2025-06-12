package org.kea.nicolainielsen.alarm;

import org.hibernate.loader.ast.internal.SingleIdArrayLoadPlan;
import org.kea.nicolainielsen.config.GeoTools;
import org.kea.nicolainielsen.fire.FireModel;
import org.kea.nicolainielsen.fire.FireServiceImpl;
import org.kea.nicolainielsen.siren.SirenModel;
import org.kea.nicolainielsen.siren.SirenRepository;
import org.kea.nicolainielsen.siren.SirenServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class AlarmServiceImpl implements AlarmService {

    private final AlarmRepository alarmRepository;
    private final SirenRepository sirenRepository;
    private SirenServiceImpl sirenServiceImpl;
    private FireServiceImpl fireServiceImpl;
    private GeoTools geoTools;

    public AlarmServiceImpl(AlarmRepository alarmRepository, SirenServiceImpl sirenServiceImpl, FireServiceImpl fireServiceImpl, GeoTools geoTools, SirenRepository sirenRepository) {
        this.alarmRepository = alarmRepository;
        this.sirenServiceImpl = sirenServiceImpl;
        this.fireServiceImpl = fireServiceImpl;
        this.geoTools = geoTools;
        this.sirenRepository = sirenRepository;
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

    // Check distance to a fire and add sirens to alarm if closer than 10K...
    @Override
    public void createAlarmAndAssignNearbySirens(int fireId, double radiusKm) {
        FireModel fire = fireServiceImpl.getFireModelbyID(fireId);

        if (fire == null) {
            throw new RuntimeException("Fire Model with ID " + fireId + " not found");
        }

        List<SirenModel> allSirens = sirenServiceImpl.findAllSirens();

        for (SirenModel siren : allSirens) {
            double distance = geoTools.distanceInKm(
                    fire.getLatitude(), fire.getLongitude(),
                    siren.getLatitude(), siren.getLongitude()
            );

            if (distance <= radiusKm && siren.isFunctional()) {
                AlarmModel alarm = new AlarmModel();
                alarm.setFire(fire);
                alarm.setSiren(siren);
                alarm.setAlarmStarted(LocalDateTime.now());
                alarm.setActive(true);
                alarmRepository.save(alarm);

                siren.setActive(true);
                siren.setLastActived(LocalDateTime.now());
                sirenServiceImpl.save(siren);
                System.out.println("Alarm created for siren: " + siren.getName());
            }
        }
    }

    @Override
    public List<AlarmModel> findByFireIdAndActiveTrue(int fireId) {
        List<AlarmModel> allAlarmsRelatedToFireID = new ArrayList<>();
        List<AlarmModel> allAlarms = alarmRepository.findAll();

        for (AlarmModel alarm : allAlarms) {
            if (alarm.getFire().getId() == fireId) {
                allAlarmsRelatedToFireID.add(alarm);
            }
        }
        return allAlarmsRelatedToFireID;
    }

    // Stop alarms ...
    @Override
    public void stopAlarmAndUpdateSiren(int alarmId) {
        AlarmModel alarm = findById(alarmId);
        if (alarm != null) {
            alarm.setActive(false);
            alarm.setAlarmEnded(LocalDateTime.now());
            save(alarm);

            // Opdater siren
            SirenModel siren = alarm.getSiren();
            siren.setLastActived(LocalDateTime.now());
            // Tilføj eventuelt andre opdateringer af siren her
            sirenRepository.save(siren);
        }
    }



    public void delete(AlarmModel alarmModel) {
        alarmRepository.delete(alarmModel);
    }
}
