package org.kea.nicolainielsen.alarm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
@Entity
public class AlarmModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int sirenID;
    private int fireID;
    private LocalDateTime alarmStarted;
    private LocalDateTime alarmEnded;
    private boolean active;

}
