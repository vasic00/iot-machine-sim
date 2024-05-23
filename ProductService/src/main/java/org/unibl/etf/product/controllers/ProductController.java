package org.unibl.etf.product.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.product.model.Plate;
import org.unibl.etf.product.model.PlateProcessed;
import org.unibl.etf.product.services.PlateService;

@RestController
@CrossOrigin
@RequestMapping("/api/products/plates/")
public class ProductController {

	private PlateService plateService;

	public ProductController(PlateService plateService) {
		super();
		this.plateService = plateService;
	}
	
	@GetMapping
	public ResponseEntity<List<Plate>> getAllPlates() {
		return new ResponseEntity<>(plateService.findAll(), HttpStatus.OK);
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<?> addPlate(@PathVariable("id")int id) {
		plateService.add(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PlateProcessed> getById(@PathVariable("id")int id) {
		return new ResponseEntity<>(plateService.findById(id), HttpStatus.OK);
	}
}
