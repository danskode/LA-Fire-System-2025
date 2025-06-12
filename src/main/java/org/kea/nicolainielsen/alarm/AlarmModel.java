package org.kea.nicolainielsen.alarm;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
@Entity
public class AlarmModel {

    @Id
    private int id;
    int sirenID;
//    @ManyToMany(mappedBy = "id")
}
