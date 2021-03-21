package com.cognizant.pharmacysupply.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cognizant.pharmacysupply.exception.MedicineNotFoundException;
import com.cognizant.pharmacysupply.exception.TokenValidationFailedException;
import com.cognizant.pharmacysupply.feignclient.AuthenticationFeignClient;
import com.cognizant.pharmacysupply.feignclient.StockFeignClient;
import com.cognizant.pharmacysupply.model.JwtResponse;
import com.cognizant.pharmacysupply.model.MedicineDemand;
import com.cognizant.pharmacysupply.model.MedicineStock;
import com.cognizant.pharmacysupply.model.PharmacyMedicineSupply;
import com.cognizant.pharmacysupply.repository.MedicineDemandRepository;
import com.cognizant.pharmacysupply.repository.PharmacyMedicineSupplyRepository;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PharmacyService {
	@Autowired
	private MedicineDemandRepository medicineDemandRepo;

	@Autowired
	private StockFeignClient stockFeignClient;

	@Autowired
	private PharmacyMedicineSupplyRepository pharmacyMedicineSupplyRepository;

	@Autowired
	private AuthenticationFeignClient authFeign;

	@Autowired
	private MedicineDemandRepository medicineDemandRepository;

	public List<PharmacyMedicineSupply> getPharmacySupplyCount(String token, List<MedicineDemand> medicineDemandList)
			throws MedicineNotFoundException {
		log.info("Start");

		log.info("Medicine Demand List {} ", medicineDemandList);
		List<PharmacyMedicineSupply> list = new ArrayList<>();
		for (Iterator<?> iterator = medicineDemandList.iterator(); iterator.hasNext();) {
			MedicineDemand medicineDemand = (MedicineDemand) iterator.next();
			PharmacyMedicineSupply pharmacyMedicineSupply = new PharmacyMedicineSupply();
			MedicineStock medicineStock = getNumberOfTablets(token, medicineDemand);
			log.info("{}", medicineStock);
			log.info("Medicine {} , Tablets {}", medicineDemand.getMedicineName(), medicineStock.getNumberOfTabletsInStock());
			
			if (medicineStock.getNumberOfTabletsInStock() < medicineDemand.getDemandCount()) {
				return null;
			}
			
			setSupply(token, pharmacyMedicineSupply, medicineDemand, medicineStock);
			list.add(pharmacyMedicineSupply);
		}
		pharmacyMedicineSupplyRepository.saveAll(list);
		medicineDemandRepository.saveAll(medicineDemandList);
		log.info("End");
		return list;
	}

	private void setSupply(String token, PharmacyMedicineSupply medicineSupply, MedicineDemand medicineDemand,
			MedicineStock medicineStock) throws MedicineNotFoundException {
		log.info("Start");
		int val = 0;
		log.info("number of tablets {}", medicineStock.getNumberOfTabletsInStock());
		if (medicineStock.getNumberOfTabletsInStock() < medicineDemand.getDemandCount()) {
			medicineSupply.setSupplyCount(medicineStock.getNumberOfTabletsInStock());

		} else {
			medicineSupply.setSupplyCount(medicineDemand.getDemandCount());
			val = medicineStock.getNumberOfTabletsInStock() - medicineDemand.getDemandCount();

		}
		log.info("val = {}", val);
		try {
			stockFeignClient.updateNumberOfTabletsInStockByName(token, medicineDemand.getMedicineName(), val);
		} catch (FeignException ex) {
			throw new MedicineNotFoundException("Medicine not found");
		}
		medicineSupply.setMedicineName(medicineDemand.getMedicineName());
		log.info("medicineDemand {} medicineSupply {}", medicineDemand, medicineSupply);
		medicineSupply.setPharmacyName(medicineStock.getPharmacyName());
		log.info("medicineSupply{}:", medicineSupply);
		log.info("End");
	}

	private MedicineStock getNumberOfTablets(String token, MedicineDemand medicineDemand)
			throws MedicineNotFoundException {
		// TODO Auto-generated method stub
		log.info("Start");
		MedicineStock medicineStock = null;
		log.info("Medicine : {}", medicineDemand);
		try {
			medicineStock = stockFeignClient.getNumberOfTabletsInStockByName(token, medicineDemand.getMedicineName());
		} catch (FeignException ex) {
			throw new MedicineNotFoundException("Medicine not found");
		}

		if (medicineStock == null) {
			throw new MedicineNotFoundException("Medicine not found");
		}
		log.info("End");
		return medicineStock;
	}

	public List<MedicineDemand> getMedicineDemand() {
		log.info("Start");
		return medicineDemandRepo.findAll();
	}

	public List<PharmacyMedicineSupply> getMedicineSupply() {
		log.info("Start");
		return pharmacyMedicineSupplyRepository.findAll();
	}

	public Boolean validateToken(String token) {
		log.info("Start");

		JwtResponse jwtResponse = authFeign.verifyToken(token);
		log.info("End");

		if (jwtResponse.isValid())
			return true;
		throw new TokenValidationFailedException("Token is no longer valid");

	}
}
