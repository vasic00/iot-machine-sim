package org.unibl.etf.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unibl.etf.product.model.PlateProcessed;

@Repository
public interface PlateProcessedRepository extends JpaRepository<PlateProcessed, Integer>{

	PlateProcessed findByPlateId(int plateId);
}
