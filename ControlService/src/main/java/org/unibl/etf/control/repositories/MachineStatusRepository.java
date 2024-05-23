package org.unibl.etf.control.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unibl.etf.control.model.MachineStatus;

@Repository
public interface MachineStatusRepository extends JpaRepository<MachineStatus, Integer>{

}
