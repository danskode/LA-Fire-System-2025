package org.kea.nicolainielsen.siren;


import org.kea.nicolainielsen.fire.FireModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sirens")
public class SirenController {

    private final SirenServiceImpl sirenServiceImpl;
    private SirenService sirenService;
    public SirenController(SirenService sirenService, SirenServiceImpl sirenServiceImpl) {
        this.sirenService = sirenService;
        this.sirenServiceImpl = sirenServiceImpl;
    }

    // CRUDs ..

    // findAll ...

    @GetMapping("")
    public ResponseEntity<List<SirenModel>> findAllSirens() {
        if (sirenService.findAllSirens() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(sirenService.findAllSirens());
    }

    // Find by id ...

    @GetMapping("{id}")
    public ResponseEntity<SirenModel>  getSireneModelbyID(@PathVariable Integer id) {
        SirenModel returned = sirenService.getSireneModelbyID(id);
        if (returned == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(returned);
    }

    // Add new siren ...

    @PostMapping("")
    ResponseEntity<SirenModel> addSiren(@RequestBody SirenModel sirenModel) {

        if (sirenModel == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        SirenModel savedSiren = sirenServiceImpl.save(sirenModel);
        return new ResponseEntity<>(savedSiren, HttpStatus.CREATED);
    }


    // Edit a siren ...

    @PutMapping("/{id}")
    public  ResponseEntity<SirenModel> updateSiren(@PathVariable int id, @RequestBody SirenModel updatedSirenModel) {
        SirenModel existingSirenModel = sirenService.getSireneModelbyID(id);
        if (existingSirenModel == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingSirenModel.setName(updatedSirenModel.getName());
        existingSirenModel.setLatitude(updatedSirenModel.getLatitude());
        existingSirenModel.setLongitude(updatedSirenModel.getLongitude());
        existingSirenModel.setActive(updatedSirenModel.isActive());
        existingSirenModel.setFunctional(updatedSirenModel.isFunctional());

        SirenModel savedSirenModel = sirenService.save(existingSirenModel);
        return new ResponseEntity<>(savedSirenModel, HttpStatus.OK);
    }


    // Delete siren ...

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteSiren(@PathVariable int id) {
        SirenModel deleteMe = sirenServiceImpl.getSireneModelbyID(id);
        sirenServiceImpl.delete(deleteMe);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
