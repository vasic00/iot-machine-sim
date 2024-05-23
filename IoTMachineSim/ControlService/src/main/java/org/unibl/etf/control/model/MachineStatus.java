package org.unibl.etf.control.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name="machine_status")
@NamedQuery(name="MachineStatus.findAll", query="SELECT m FROM MachineStatus m")
public class MachineStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="machine_id")
	private int machineId;

	private String status;
	
	private String datetime;

	public MachineStatus() {
	}
	
	public MachineStatus(int id, int machineId, String status, String datetime) {
		super();
		this.id = id;
		this.machineId = machineId;
		this.status = status;
		this.datetime = datetime;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	
	

}