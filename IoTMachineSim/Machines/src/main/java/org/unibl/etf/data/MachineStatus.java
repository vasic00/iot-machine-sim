package org.unibl.etf.data;

public class MachineStatus {

	int id;
	int machineId;
	String status;
	String datetime;
	
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
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMachineId() {
		return machineId;
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
