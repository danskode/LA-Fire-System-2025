package org.kea.nicolainielsen.fire;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FireRepository extends JpaRepository<FireModel, Integer>{

}
