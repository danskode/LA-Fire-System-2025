package org.kea.nicolainielsen.alarm;

import org.hibernate.loader.ast.internal.SingleIdArrayLoadPlan;
import org.kea.nicolainielsen.fire.FireModel;
import org.kea.nicolainielsen.fire.FireServiceImpl;
import org.kea.nicolainielsen.siren.SirenModel;
import org.kea.nicolainielsen.siren.SirenServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class AlarmServiceImpl implements AlarmService {

    private final AlarmRepository alarmRepository;
    private SirenServiceImpl sirenServiceImpl;
    private FireServiceImpl fireServiceImpl;
    public AlarmServiceImpl(AlarmRepository alarmRepository,  SirenServiceImpl sirenServiceImpl, FireServiceImpl fireServiceImpl) {
        this.alarmRepository = alarmRepository;
        this.sirenServiceImpl = sirenServiceImpl;
        this.fireServiceImpl = fireServiceImpl;
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
            new RuntimeException("Fire Model with ID " + fireId + " not found");
        }

        List<SirenModel> allSirens = sirenServiceImpl.findAllSirens();

        for (SirenModel siren : allSirens) {
            double distance = calculateDistance(
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
                sirenServiceImpl.save(siren);
            }
        }
    }

    // Haversine-formula ... something clever rom ChatGPT ...
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
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

    public void delete(AlarmModel alarmModel) {
        alarmRepository.delete(alarmModel);
    }
}
