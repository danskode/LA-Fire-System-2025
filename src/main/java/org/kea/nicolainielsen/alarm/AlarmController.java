package org.kea.nicolainielsen.alarm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController("/api/alarms")
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

    // Add new alarm ...

    // Update an alarm ...




}
