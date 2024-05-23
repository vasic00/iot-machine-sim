package org.unibl.etf.control.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.control.model.MachineStatus;
import org.unibl.etf.control.services.MachineService;

@RestController
@CrossOrigin
@RequestMapping("/api/control/machines")
public class MachineController {

	private MachineService machineService;
	
	public MachineController(MachineService machineService) {
		this.machineService = machineService;
	}
	
	@PutMapping("/status/{id}/{command}")
	public ResponseEntity<?> changeMachineStatus(@PathVariable("id") int id, @PathVariable("command") String command) {
		boolean flag = machineService.publish(id, command);
		if (flag)
			return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/status")
	public ResponseEntity<List<MachineStatus>> getAllStatus() {
		return new ResponseEntity<>(machineService.findAllStatus(), HttpStatus.OK);
	}
	
}
