package org.unibl.etf.product.services;

import java.util.List;

import org.unibl.etf.product.model.Plate;
import org.unibl.etf.product.model.PlateProcessed;

public interface PlateService {

	void add(int id);
	List<Plate> findAll();
	PlateProcessed findById(int plateId);
}
