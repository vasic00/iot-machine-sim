package org.unibl.etf.data;

public class PlateProcessed {

	int id;
	String finished;
	int plateId;
	String started;
	
	public PlateProcessed() {
		
	}

	public PlateProcessed(int id, String finished, int plateId, String started) {
		super();
		this.id = id;
		this.finished = finished;
		this.plateId = plateId;
		this.started = started;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFinished() {
		return finished;
	}

	public void setFinished(String finished) {
		this.finished = finished;
	}

	public int getPlateId() {
		return plateId;
	}

	public void setPlateId(int plateId) {
		this.plateId = plateId;
	}

	public String getStarted() {
		return started;
	}

	public void setStarted(String started) {
		this.started = started;
	}
	
	
}
