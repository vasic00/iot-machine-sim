package org.unibl.etf.control.services;

import java.util.List;

import org.unibl.etf.control.model.MachineStatus;

public interface MachineService {
	
	List<MachineStatus> findAllStatus();
	boolean publish(int machineId, String command);
}
