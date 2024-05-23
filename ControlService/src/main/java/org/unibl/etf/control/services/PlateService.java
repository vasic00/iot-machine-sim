package org.unibl.etf.control.services;

import java.util.List;

import org.unibl.etf.control.model.PlateProcessed;

public interface PlateService {

	List<PlateProcessed> findAll();
}
