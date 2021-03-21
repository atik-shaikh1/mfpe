package com.cognizant.medicinestock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.medicinestock.model.MedicineStock;
import com.cognizant.medicinestock.repository.MedicineStockRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MedicineStockService {

	@Autowired
	private MedicineStockRepository repository;
	
	
	public List<MedicineStock> getMedicineStockInformation() {
		log.info("Start");
		log.info("End");
		return repository.findAll();
	}
	
	public List<MedicineStock> getMedicineByTargetAilment(String treatingAilment) {
		log.info("Start");
		log.info("End");
		return repository.getMedicineByTargetAilment(treatingAilment);
	}

	/**
	 * Get the count of tablets present in the stock for a given medicine
	 * 
	 * @see MedicineStock
	 * @see MedicineStockRepository
	 */
	public MedicineStock getNumberOfTabletsInStockByName(String medicine) {
		log.info("Start");
		log.info("End");
		MedicineStock numberOfTabletsInStockByName = repository.getNumberOfTabletsInStockByName(medicine);
		log.debug("numberOfTabletsInStockByName{}:", numberOfTabletsInStockByName);
		return numberOfTabletsInStockByName;
	}

	/**
	 * Updates the number of tablets in stock by name of medicine
	 * 
	 * @param medicine which is supplied by pharmacy
	 * @param count    number of tablets supplied by pharmacy
	 * 
	 * @see MedicineStockRepository
	 */
	public Boolean updateNumberOfTabletsInStockByName(String medicine, int count) {
		log.info("Start");
		log.info(medicine + " ############# " + count);
		repository.updateNumberOfTabletsInStockByName(medicine, count);
		log.info("End");
		return true;
	}

}
