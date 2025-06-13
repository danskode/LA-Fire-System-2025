package org.kea.nicolainielsen.siren;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.kea.nicolainielsen.alarm.AlarmModel;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class SirenModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100)
    private String name;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private boolean functional;

    private LocalDateTime lastActivated;

    @OneToMany(mappedBy = "siren", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AlarmModel> alarmModels = new ArrayList<>();

}
