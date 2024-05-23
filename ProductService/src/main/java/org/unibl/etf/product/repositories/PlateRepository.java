package org.unibl.etf.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unibl.etf.product.model.Plate;

@Repository
public interface PlateRepository extends JpaRepository<Plate, Integer>{

}
