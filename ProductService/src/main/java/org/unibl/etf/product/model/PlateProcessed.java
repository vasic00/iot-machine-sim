package org.unibl.etf.product.model;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the plate_processed database table.
 * 
 */
@Entity
@Table(name="plate_processed")
@NamedQuery(name="PlateProcessed.findAll", query="SELECT p FROM PlateProcessed p")
public class PlateProcessed implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String finished;

	@Column(name="plate_id")
	private int plateId;

	private String started;

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
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFinished() {
		return this.finished;
	}

	public void setFinished(String finished) {
		this.finished = finished;
	}

	public int getPlateId() {
		return this.plateId;
	}

	public void setPlateId(int plateId) {
		this.plateId = plateId;
	}

	public String getStarted() {
		return this.started;
	}

	public void setStarted(String started) {
		this.started = started;
	}

}