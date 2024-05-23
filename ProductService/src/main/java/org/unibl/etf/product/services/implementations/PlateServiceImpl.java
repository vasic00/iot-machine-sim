package org.unibl.etf.product.services.implementations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.unibl.etf.product.model.Plate;
import org.unibl.etf.product.model.PlateProcessed;
import org.unibl.etf.product.repositories.PlateProcessedRepository;
import org.unibl.etf.product.repositories.PlateRepository;
import org.unibl.etf.product.services.PlateService;

@Service
public class PlateServiceImpl implements PlateService{

	private static final Logger infoLogger = LoggerFactory.getLogger(PlateServiceImpl.class);
	
	private PlateRepository plateRepository;
	private PlateProcessedRepository plateProcessedRepository;
	
	public PlateServiceImpl(PlateRepository plateRepository, PlateProcessedRepository plateProcessedRepository) {
		super();
		this.plateRepository = plateRepository;
		this.plateProcessedRepository = plateProcessedRepository;
	}

	@Override
	public List<Plate> findAll() {
		infoLogger.info("Request for all plates");
		return plateRepository.findAll();
	}

	@Override
	public PlateProcessed findById(int plateId) {
		infoLogger.info("Request for plate by id: " + plateId);
		return plateProcessedRepository.findByPlateId(plateId);
	}

	@Override
	public void add(int id) {
		plateRepository.saveAndFlush(new Plate(id));
		infoLogger.info("Saved plate with id: " + id);
	}

}
