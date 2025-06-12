package org.kea.nicolainielsen.fire;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/fires")
public class FireController {

    private final FireServiceImpl fireServiceImpl;
    private FireServiceImpl fireService;

    public FireController(FireServiceImpl fireService, FireServiceImpl fireServiceImpl) {
        this.fireService = fireService;
        this.fireServiceImpl = fireServiceImpl;
    }

    // Get all fires ever registered ...

    @GetMapping("")
    ResponseEntity<List<FireModel>> findAllFires() {
        List<FireModel> fireModels = fireService.findAllFires();
        if (fireModels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(fireModels, HttpStatus.OK);
    }

    // Get a specific fires by its id ...

    @GetMapping("/{id}")
    public ResponseEntity<FireModel> getFireById(@PathVariable Integer id){

        FireModel returned = fireService.getFireModelbyID(id);
        if (returned == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (returned != null) {
            return new ResponseEntity<>(returned, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Create a new fire ...

    @PostMapping("")
    public ResponseEntity<FireModel> addFire(@RequestBody FireModel fireModel) {
        FireModel savedFireModel = fireService.save(fireModel);
        return new ResponseEntity<>(savedFireModel, HttpStatus.CREATED);
    }


    // Edit a fire ...

    @PutMapping("/{id}")
    public  ResponseEntity<FireModel> updateFire(@PathVariable int id, @RequestBody FireModel updatedFireModel) {

        FireModel existingFireModel = fireService.getFireModelbyID(id);
        if (existingFireModel == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingFireModel.setName(updatedFireModel.getName());
        existingFireModel.setLongitude(updatedFireModel.getLongitude());
        existingFireModel.setLatitude(updatedFireModel.getLatitude());
        existingFireModel.setActive(updatedFireModel.isActive());

        FireModel savedFireModel = fireService.save(existingFireModel);
        return new ResponseEntity<>(savedFireModel, HttpStatus.OK);
    }

    //Delete a fire by its id ...

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFireById(@PathVariable int id) {
        FireModel deleteMe = fireService.getFireModelbyID(id);
        fireServiceImpl.delete(deleteMe);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
