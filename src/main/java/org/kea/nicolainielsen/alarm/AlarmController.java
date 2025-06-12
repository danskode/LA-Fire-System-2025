package org.kea.nicolainielsen.alarm;

import org.kea.nicolainielsen.fire.FireModel;
import org.kea.nicolainielsen.fire.FireServiceImpl;
import org.kea.nicolainielsen.siren.SirenModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/alarms")
public class AlarmController {

    @Autowired
    AlarmServiceImpl alarmServiceImpl;
    @Autowired
    private FireServiceImpl fireServiceImpl;

    // Get all alarms ...

    @GetMapping("")
    public ResponseEntity<List<AlarmModel>> getAll() {
        List<AlarmModel> allAlarms = alarmServiceImpl.findAll();
        return new ResponseEntity<>(allAlarms, HttpStatus.OK);
    }

    // Find one alarm by id ...

    @GetMapping("/{id}")
    public ResponseEntity<AlarmModel> getAlarmById(@PathVariable int id) {
        AlarmModel alarm = alarmServiceImpl.findById(id);
        if (alarm == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(alarm, HttpStatus.OK);
    }

    // Add new alarm ...

    @PostMapping("")
    public ResponseEntity<AlarmModel> saveAlarm(@RequestBody AlarmModel alarm) {
        alarmServiceImpl.save(alarm);
        return new ResponseEntity<>(alarm, HttpStatus.OK);
    }

    // Start an alarm and add sirens closer than 10K to it ...

    @PostMapping("/start/{fireId}")
    public ResponseEntity<String> startAlarm(@PathVariable int fireId) {
        alarmServiceImpl.createAlarmAndAssignNearbySirens(fireId, 10.0);
        return ResponseEntity.ok("Alarm started ans nearby sirens has been activated");
    }

    // Stop an alarm and deactivate related sirens ...

    @PutMapping("/stop/{id}")
    public ResponseEntity<AlarmModel> stopAlarm(@PathVariable int id) {
        AlarmModel alarm = alarmServiceImpl.findById(id);
        if (alarm == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        alarm.setActive(false);
        alarm.setAlarmEnded(LocalDateTime.now());
        alarmServiceImpl.save(alarm);

        return new ResponseEntity<>(alarm, HttpStatus.OK);
    }

    // Stop all sirens related to a specific fire through alarms...
    @PutMapping("/start/fire/{fireId}")
    public ResponseEntity<String> startAllAlarmsForFire(@PathVariable int fireId) {
        FireModel fire = fireServiceImpl.getFireModelbyID(fireId);

        if (fire == null) {
            return new ResponseEntity<>("Fire not found", HttpStatus.NOT_FOUND);
        }

        if (fire.isActive()) {
            return new ResponseEntity<>("Fire is already active", HttpStatus.OK);
        }

        // Aktiver branden
        fire.setActive(true);
        fireServiceImpl.save(fire);

        // Find og opret alarmer for nærliggende sirener
        alarmServiceImpl.createAlarmAndAssignNearbySirens(fireId, 10.0);

        return new ResponseEntity<>("Alarms started and fire marked active", HttpStatus.OK);
    }

    // Stop all sirens related to a specific fire through alarms...

    @PutMapping("/stop/fire/{fireId}")
    public ResponseEntity<String> stopAllAlarmsForFire(@PathVariable int fireId) {
        FireModel fire = fireServiceImpl.getFireModelbyID(fireId);
        List<AlarmModel> alarms = alarmServiceImpl.findByFireIdAndActiveTrue(fireId);

        if (fire != null && fire.isActive())  {
            for (AlarmModel alarm : alarms) {
                alarmServiceImpl.stopAlarmAndUpdateSiren(alarm.getId());
            }
            FireModel fireOut = fireServiceImpl.getFireModelbyID(fireId);
            fireOut.setActive(false);
            fireServiceImpl.save(fireOut);

            return new ResponseEntity<>("Fire is put out --> Status changed", HttpStatus.OK);
        } else if (!fire.isActive()){
            return new ResponseEntity<>("This fire has already been put out ...", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Somethings wrong ...", HttpStatus.NOT_FOUND);
        }
    }


    // Delete an alarm ...

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlarm(@PathVariable int id) {
        AlarmModel deleteMe = alarmServiceImpl.findById(id);
        if (deleteMe == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        alarmServiceImpl.delete(deleteMe);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
