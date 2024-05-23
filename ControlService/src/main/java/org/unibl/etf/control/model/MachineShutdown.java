package org.unibl.etf.control.model;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the machine_shutdown database table.
 * 
 */
@Entity
@Table(name="machine_shutdown")
@NamedQuery(name="MachineShutdown.findAll", query="SELECT m FROM MachineShutdown m")
public class MachineShutdown implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="machine_id")
	private int machineId;

	private String shutdown;

	public MachineShutdown() {
	}
	
	public MachineShutdown(int id, int machineId, String shutdown) {
		super();
		this.id = id;
		this.machineId = machineId;
		this.shutdown = shutdown;
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

	public String getShutdown() {
		return this.shutdown;
	}

	public void setShutdown(String shutdown) {
		this.shutdown = shutdown;
	}

}