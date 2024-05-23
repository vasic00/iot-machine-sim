package org.unibl.etf.control.model;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the machine_resume database table.
 * 
 */
@Entity
@Table(name="machine_resume")
@NamedQuery(name="MachineResume.findAll", query="SELECT m FROM MachineResume m")
public class MachineResume implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="machine_id")
	private int machineId;

	private String resumed;

	public MachineResume() {
	}
	
	public MachineResume(int id, int machineId, String resumed) {
		super();
		this.id = id;
		this.machineId = machineId;
		this.resumed = resumed;
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

	public String getResumed() {
		return this.resumed;
	}

	public void setResumed(String resumed) {
		this.resumed = resumed;
	}

}