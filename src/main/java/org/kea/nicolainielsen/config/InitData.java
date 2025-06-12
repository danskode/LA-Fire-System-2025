package org.kea.nicolainielsen.config;

import org.kea.nicolainielsen.alarm.AlarmModel;
import org.kea.nicolainielsen.alarm.AlarmServiceImpl;
import org.kea.nicolainielsen.fire.FireModel;
import org.kea.nicolainielsen.fire.FireServiceImpl;
import org.kea.nicolainielsen.siren.SirenModel;
import org.kea.nicolainielsen.siren.SirenRepository;
import org.kea.nicolainielsen.siren.SirenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InitData implements CommandLineRunner {

    @Autowired
    private FireServiceImpl fireServiceImpl;

    @Autowired
    private SirenServiceImpl sirenServiceImpl;
    @Autowired
    private SirenRepository sirenRepository;
    @Autowired
    private AlarmServiceImpl alarmServiceImpl;


    @Override
    public void run(String... args) throws Exception {
        //Insert demo fires ...
        FireModel fireModel1 = new FireModel();
        // fireModel1.setId(1);
        fireModel1.setName("Fire 1");
        fireModel1.setLatitude(100.0);
        fireModel1.setLongitude(100.0);
        fireModel1.setActive(false);
        fireModel1.setStartTime(LocalDateTime.now());
        fireServiceImpl.save(fireModel1);

        FireModel fireModel2 = new FireModel();
        // fireModel2.setId(2);
        fireModel2.setName("Fire 2");
        fireModel2.setLatitude(200.0);
        fireModel2.setLongitude(200.0);
        fireModel2.setActive(true);
        fireModel2.setStartTime(LocalDateTime.now().minusDays(12));
        fireModel2.setEndTime(LocalDateTime.now());
        fireServiceImpl.save(fireModel2);

        // Insert demo sirenes ...
        SirenModel sirenModel1 = new SirenModel();
        sirenModel1.setName("Sirene 1");
        sirenModel1.setLatitude(100.0);
        sirenModel1.setLongitude(100.0);
        sirenModel1.setActive(false);
        sirenModel1.setFunctional(false);
        sirenServiceImpl.save(sirenModel1);

        // Insert demo alarm ...
        AlarmModel alarmModel1 = new AlarmModel();
        alarmModel1.setSirenID(1);
        alarmModel1.setFireID(1);
        alarmModel1.setAlarmStarted(LocalDateTime.now());
        alarmModel1.setAlarmEnded(LocalDateTime.now());
        alarmModel1.setActive(true);
        alarmServiceImpl.save(alarmModel1);

    }

}
