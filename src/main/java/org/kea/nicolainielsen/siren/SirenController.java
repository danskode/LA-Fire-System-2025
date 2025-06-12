package org.kea.nicolainielsen.siren;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sirens")
public class SirenController {

    private SirenService sirenService;
    public SirenController(SirenService sirenService) {
        this.sirenService = sirenService;
    }

    // CRUDs ..

    @GetMapping("")
    public ResponseEntity<List<SirenModel>> findAllSirens() {
        if (sirenService.findAllSirens() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(sirenService.findAllSirens());
    }

    @GetMapping("{id}")
    public ResponseEntity<SirenModel>  getSireneModelbyID(@PathVariable Integer id) {
        SirenModel returned = sirenService.getSireneModelbyID(id);
        if (returned == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(returned);
    }
}
