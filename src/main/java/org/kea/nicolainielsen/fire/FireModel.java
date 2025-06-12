package org.kea.nicolainielsen.fire;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class FireModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(length = 100, nullable = false)
    String name;
    @Column(nullable = false)
    double latitude;
    @Column(nullable = false)
    double longitude;
    @Column(nullable = false)
    boolean active;

    LocalDateTime startTime;
    LocalDateTime endTime;

}
