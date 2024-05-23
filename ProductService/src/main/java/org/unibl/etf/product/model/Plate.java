package org.unibl.etf.product.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="plate")
public class Plate implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private int id;
	
	public Plate() {
		
	}

	public Plate(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
