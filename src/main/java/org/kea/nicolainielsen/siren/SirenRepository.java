package org.kea.nicolainielsen.siren;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SirenRepository extends JpaRepository<SirenModel, Integer> {

}
