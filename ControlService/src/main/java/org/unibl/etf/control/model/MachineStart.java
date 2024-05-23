package org.unibl.etf.control.model;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the machine_start database table.
 * 
 */
@Entity
@Table(name="machine_start")
@NamedQuery(name="MachineStart.findAll", query="SELECT m FROM MachineStart m")
public class MachineStart implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="machine_id")
	private int machineId;

	private String started;

	public MachineStart() {
	}
	
	public MachineStart(int id, int machineId, String started) {
		super();
		this.id = id;
		this.machineId = machineId;
		this.started = started;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMachineId() {
		return this.machineId;
	}

	public void setMachineId(int machineId) {
		this.machineId = machineId;
	}

	public String getStarted() {
		return this.started;
	}

	public void setStarted(String started) {
		this.started = started;
	}

}