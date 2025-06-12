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
import java.util.ArrayList;
import java.util.List;

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

        // Insert demo fires
        FireModel fireModel1 = new FireModel();
        fireModel1.setName("Santa Monica Fire");
        fireModel1.setLatitude(34.0100);
        fireModel1.setLongitude(-118.4960);
        fireModel1.setActive(true);
        fireServiceImpl.save(fireModel1);

        FireModel fireModel2 = new FireModel();
        fireModel2.setName("Pacific Palisades Fire");
        fireModel2.setLatitude(34.0356);
        fireModel2.setLongitude(-118.5153);
        fireModel2.setActive(false);
        fireServiceImpl.save(fireModel2);

        // Insert demo sirens
        List<SirenModel> sirens = new ArrayList<>();

        double[][] sirenCoords = {
                {34.0090, -118.4950},
                {34.0120, -118.4920},
                {34.0110, -118.4980},
                {34.0300, -118.5200},
                {34.0315, -118.5100},
                {34.0330, -118.5080},
                {34.0280, -118.5070},
                {34.0270, -118.5190},
                {34.0220, -118.5160},
                {34.0360, -118.5140},
                {34.0380, -118.5170}
        };

        for (int i = 0; i < sirenCoords.length; i++) {
            SirenModel siren = new SirenModel();
            siren.setName("Sirene " + (i + 1));
            siren.setLatitude(sirenCoords[i][0]);
            siren.setLongitude(sirenCoords[i][1]);
            siren.setActive(true);
            siren.setFunctional(true);
            sirenServiceImpl.save(siren);
            sirens.add(siren);
        }

        // Insert demo alarms close to fires ...
        for (int i = 0; i < 5; i++) {
            AlarmModel alarm = new AlarmModel();
            alarm.setFire(fireModel1); // Santa Monica Fire
            alarm.setSiren(sirens.get(i));
            alarm.setAlarmStarted(LocalDateTime.now().minusMinutes(30));
            alarm.setActive(true);
            alarmServiceImpl.save(alarm);
        }

        // historical data ...
        for (int i = 5; i < 8; i++) {
            AlarmModel alarm = new AlarmModel();
            alarm.setFire(fireModel2); // Pacific Palisades Fire
            alarm.setSiren(sirens.get(i));
            alarm.setAlarmStarted(LocalDateTime.now().minusDays(1));
            alarm.setAlarmEnded(LocalDateTime.now().minusHours(12));
            alarm.setActive(false);
            alarmServiceImpl.save(alarm);
        }
    }
}