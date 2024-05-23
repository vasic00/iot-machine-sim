package org.unibl.etf.control.model;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the machine_pause database table.
 * 
 */
@Entity
@Table(name="machine_pause")
@NamedQuery(name="MachinePause.findAll", query="SELECT m FROM MachinePause m")
public class MachinePause implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="machine_id")
	private int machineId;

	private String paused;

	public MachinePause() {
	}
	
	public MachinePause(int id, int machineId, String paused) {
		super();
		this.id = id;
		this.machineId = machineId;
		this.paused = paused;
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

	public String getPaused() {
		return this.paused;
	}

	public void setPaused(String paused) {
		this.paused = paused;
	}

}