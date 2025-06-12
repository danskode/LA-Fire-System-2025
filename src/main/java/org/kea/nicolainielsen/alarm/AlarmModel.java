package org.kea.nicolainielsen.alarm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.kea.nicolainielsen.fire.FireModel;
import org.kea.nicolainielsen.siren.SirenModel;

import java.time.LocalDateTime;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
@Entity
public class AlarmModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "fire_id", nullable = false)
    private FireModel fire;

    @ManyToOne
    @JoinColumn(name = "siren_id", nullable = false)
    private SirenModel siren;

    private LocalDateTime alarmStarted;
    private LocalDateTime alarmEnded;
    private boolean active;

}
