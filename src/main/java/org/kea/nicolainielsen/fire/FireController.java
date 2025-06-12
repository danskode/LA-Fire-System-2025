package org.kea.nicolainielsen.fire;

import org.kea.nicolainielsen.alarm.AlarmModel;
import org.kea.nicolainielsen.alarm.AlarmServiceImpl;
import org.kea.nicolainielsen.siren.SirenServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/fires")
public class FireController {

    private final FireServiceImpl fireServiceImpl;
    private final AlarmServiceImpl alarmServiceImpl;
    private final SirenServiceImpl sirenServiceImpl;

    public FireController(FireServiceImpl fireServiceImpl, AlarmServiceImpl alarmServiceImpl, SirenServiceImpl sirenServiceImpl) {
        this.fireServiceImpl = fireServiceImpl;
        this.alarmServiceImpl = alarmServiceImpl;
        this.sirenServiceImpl = sirenServiceImpl;
    }

    // Get all fires ever registered ...
    @GetMapping("")
    ResponseEntity<List<FireModel>> findAllFires() {
        List<FireModel> fireModels = fireServiceImpl.findAllFires();
        if (fireModels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(fireModels, HttpStatus.OK);
    }

    // Get a specific fires by its id ...
    @GetMapping("/{id}")
    public ResponseEntity<FireModel> getFireById(@PathVariable Integer id){

        FireModel returned = fireServiceImpl.getFireModelbyID(id);
        if (returned == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (returned != null) {
            return new ResponseEntity<>(returned, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Get active fires ...
    @GetMapping("/active")
    public ResponseEntity<List<FireModel>> allActiveFires() {
        List<FireModel> activeFires = fireServiceImpl.findActiveFires();
        return new ResponseEntity<>(activeFires, HttpStatus.OK);
    }


    // Create a new fire ...
    @PostMapping("")
    public ResponseEntity<FireModel> addFire(@RequestBody FireModel fireModel) {
        FireModel savedFireModel = fireServiceImpl.save(fireModel);
        return new ResponseEntity<>(savedFireModel, HttpStatus.CREATED);
    }


    // Edit a fire ...
    @PutMapping("/{id}")
    public  ResponseEntity<FireModel> updateFire(@PathVariable int id, @RequestBody FireModel updatedFireModel) {
        FireModel existingFireModel = fireServiceImpl.getFireModelbyID(id);
        if (existingFireModel == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingFireModel.setName(updatedFireModel.getName());
        existingFireModel.setLongitude(updatedFireModel.getLongitude());
        existingFireModel.setLatitude(updatedFireModel.getLatitude());
        existingFireModel.setActive(updatedFireModel.isActive());

        FireModel savedFireModel = fireServiceImpl.save(existingFireModel);
        return new ResponseEntity<>(savedFireModel, HttpStatus.OK);
    }

    //Deactivate fire ...
    @PutMapping("/deactivate/{id}")
    public  ResponseEntity<FireModel> deactivateFire(@PathVariable int id, @RequestBody FireModel updatedFireModel) {
        FireModel existingFireModel = fireServiceImpl.getFireModelbyID(id);
        if (existingFireModel == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingFireModel.setActive(updatedFireModel.isActive());

        List<AlarmModel> allAlarms = alarmServiceImpl.findAll();
        for (AlarmModel alarmModel : allAlarms) {
            if (alarmModel.getFire().equals(existingFireModel)) {
                alarmModel.setActive(false);
                alarmModel.getSiren().setActive(false);
                alarmServiceImpl.save(alarmModel);
            }
        }

        FireModel savedFireModel = fireServiceImpl.save(existingFireModel);
        return new ResponseEntity<>(savedFireModel, HttpStatus.OK);
    }

    //Delete a fire by its id ...
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFireById(@PathVariable int id) {
        FireModel deleteMe = fireServiceImpl.getFireModelbyID(id);
        fireServiceImpl.delete(deleteMe);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
