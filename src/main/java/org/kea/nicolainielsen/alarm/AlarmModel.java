package org.kea.nicolainielsen.alarm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
@Entity
public class AlarmModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    int sirenID;
}
