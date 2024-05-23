package org.unibl.etf.control.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unibl.etf.control.model.PlateProcessed;

@Repository
public interface PlateProcessedRepository extends JpaRepository<PlateProcessed, Integer>{

}
