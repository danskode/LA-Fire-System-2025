package org.kea.nicolainielsen.alarm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<AlarmModel,Integer> {

    public List<AlarmModel> findAll();

    public AlarmModel findById(int id);

}
