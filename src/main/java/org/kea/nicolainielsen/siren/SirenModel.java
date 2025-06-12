package org.kea.nicolainielsen.siren;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class SirenModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(length = 100)
    String name;
    @Column(nullable = false)
    double latitude;
    @Column(nullable = false)
    double longitude;
    @Column(nullable = false)
    boolean active;

    @Column(nullable = false)
    boolean functional;

}
