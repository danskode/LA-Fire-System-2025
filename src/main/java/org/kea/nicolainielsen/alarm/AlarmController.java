package org.kea.nicolainielsen.alarm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/alarms")
public class AlarmController {

    @Autowired
    AlarmServiceImpl alarmServiceImpl;

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

    // Update an alarm ...

    @PutMapping("/{id}")
    public ResponseEntity<AlarmModel> updateAlarm(@PathVariable int id, @RequestBody AlarmModel updatedalarm) {
        AlarmModel alarm = alarmServiceImpl.findById(id);
        if (alarm == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        alarm.setAlarmStarted(updatedalarm.getAlarmStarted());
        alarm.setAlarmEnded(updatedalarm.getAlarmEnded());
        alarm.setFire(updatedalarm.getFire());
        alarm.setSiren(updatedalarm.getSiren());
        alarm.setActive(updatedalarm.isActive());
        alarmServiceImpl.save(alarm);
        return new ResponseEntity<>(alarm, HttpStatus.OK);
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
