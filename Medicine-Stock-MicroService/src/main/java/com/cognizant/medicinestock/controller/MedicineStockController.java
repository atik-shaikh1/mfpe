package com.cognizant.medicinestock.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.medicinestock.exception.TokenValidationFailedException;
import com.cognizant.medicinestock.feignclient.AuthFeignClient;
import com.cognizant.medicinestock.model.AuthResponse;
import com.cognizant.medicinestock.model.MedicineStock;
import com.cognizant.medicinestock.service.MedicineStockService;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Pod3
 * @version 1.8
 * @apiNote This class is handling all the end points for medicine stock
 *          information. This controller has only one mappings which will be
 *          used to extract information of medicine stock. It will use an
 *          interface {@link MedicineStockService} for extracting information
 *          about medicine stock. AuthFeignClient is used to verify the token.
 * 
 * @see RepScheduleService
 * @see AuthFeignClient
 */
@RestController
@Slf4j
public class MedicineStockController {

	/**
	 * It provides feign service. For validating token its method will be called
	 * whose implementation will be in authorization service.
	 * 
	 * @see AuthFeignClient
	 */
	@Autowired
	private AuthFeignClient authFeignClient;

	/**
	 * An interface which has an implementation class
	 * {@link MedicineStockServiceImpl} that contains the business logic for
	 * extracting information about medicine stock
	 * 
	 * @see MedicineStockService
	 * @see MedicineStockServiceImpl
	 */
	@Autowired
	private MedicineStockService medicineStockService;

	/**
	 * It will return the medicine stock information. First it wil take token as a
	 * parameter and based on that it will call authClient method to validtae token
	 * if token valid then extract information
	 * 
	 * @see AuthResponse
	 * @see AuthFeignClient
	 * @see MedicineStockService
	 * @see MedicineStockServiceImpl
	 * 
	 * @param token It will come from the authorization header which will be sent to
	 *              authorization service for validation. If token is valid then
	 *              only we will continue further.
	 * @return the information about the medicine present in stock
	 */
	@GetMapping("/medicine-stock-information")
	public ResponseEntity<?> getMedicineStockInformation(@RequestHeader(name = "Authorization") String token) {
		log.info("Start");
		log.debug("token{}:", token);
		List<MedicineStock> medicineStockInformation = null;
		try {
			AuthResponse authResponse = authFeignClient.verifyToken(token);
			log.debug("authResponse{}:", authResponse);
			if (authResponse.isValid()) {
				log.info("Token is valid");
				medicineStockInformation = medicineStockService.getMedicineStockInformation();
			}
		} catch (FeignException e) {
			log.error("Token validation failed");
			throw new TokenValidationFailedException("Token Expired");
		}
		return new ResponseEntity<>(medicineStockInformation, HttpStatus.OK);

	}

	/**
	 * Based on the treating ailment this method will return the information about
	 * the medicine. After fetching details in a list, from the list one by one
	 * extract each medicineStock object and fetch medicine from it and store these
	 * in an array and return it.
	 * 
	 * @see AuthResponse
	 * @see MedicineStockServiceImpl
	 * @see MedicineStockService
	 * 
	 * @param token           It will come from the authorization header which will
	 *                        be sent to auhorization service for validation. If
	 *                        token is valid then only we will continue further.
	 * @param treatingAilment
	 * @return the medicine specific for treating ailment
	 */
	@PostMapping("/byTreatingAilment/{treatingAilment}")
	public ResponseEntity<?> getMedicineByTreatingAilment(@RequestHeader(name = "Authorization") String token,
			@PathVariable("treatingAilment") String treatingAilment) {
		log.info("Start");
		log.debug("token{}:", token);
		log.debug("treatingAilment{}:", treatingAilment);
		List<String> medicines = new ArrayList<>();
		try {
			AuthResponse authResponse = authFeignClient.verifyToken(token);
			log.debug("authResponse{}:", authResponse);
			if (authResponse.isValid()) {
				log.info("Token is valid");
				List<MedicineStock> medicineByTargetAilment = medicineStockService
						.getMedicineByTargetAilment(treatingAilment);
				log.debug("medicineByTargetAilment{}: ", medicineByTargetAilment);
				for (Iterator iterator = medicineByTargetAilment.iterator(); iterator.hasNext();) {
					MedicineStock medicineStock = (MedicineStock) iterator.next();
					medicines.add(medicineStock.getName());
				}
			}
		} catch (FeignException e) {
			log.error("Token validation failed");
			throw new TokenValidationFailedException("Token Expired");
		}
		return new ResponseEntity<>(medicines.toArray(new String[0]), HttpStatus.OK);

	}

	/**
	 * Fetch the stock count for the given medicine. Call the
	 * {@link MedicineStockServiceImpl} method to extract the result
	 * 
	 * @see MedicineStockServiceImpl
	 * @see FeignException
	 * @see AuthResponse
	 * @see MedicineStock
	 * @see MedicineStockService
	 * 
	 * @param token    It will come from the authorization header which will be sent
	 *                 to authorization service for validation. If token is valid
	 *                 then only we will continue further.
	 * @param medicine
	 * @return the count of a particular medicine from stock
	 */
	@PostMapping("/get-stock-count/{medicine}")
	public ResponseEntity<?> getStockCountForMedicine(@RequestHeader(name = "Authorization") String token,
			@PathVariable("medicine") String medicine) {
		log.info("Start");

		MedicineStock medicineStock = null;
		try {
			AuthResponse authResponse = authFeignClient.verifyToken(token);
			if (authResponse.isValid()) {
				medicineStock = medicineStockService.getNumberOfTabletsInStockByName(medicine);
			}
		} catch (FeignException e) {
			throw new TokenValidationFailedException("Token Expired");
		}

		return new ResponseEntity<>(medicineStock, HttpStatus.OK);
	}

	/**
	 * Updates the number of tablets in stock by name of medicine.Call the
	 * {@link MedicineStockServiceImpl} method to extract the result
	 * 
	 * @param token    It will come from the authorization header which will be sent
	 *                 to authorization service for validation. If token is valid
	 *                 then only we will continue further.
	 * @param medicine which is supplied by pharmacy
	 * @param count    number of tablets supplied by pharmacy
	 * 
	 * @see AuthResponse
	 * @see FeignException
	 * @see MedicineStockService
	 * @see MedicineStockServiceImpl
	 */
	@PostMapping("/update-stock/{medicine}/{count}")
	public Boolean updateNumberOfTabletsInStockByName(@RequestHeader(name = "Authorization") String token,
			@PathVariable("medicine") String medicine, @PathVariable("count") int count) {

		log.info("Start");
		Boolean ans = false;

		try {
			AuthResponse authResponse = authFeignClient.verifyToken(token);
			if (authResponse.isValid()) {
				ans = medicineStockService.updateNumberOfTabletsInStockByName(medicine, count);
			}
		} catch (FeignException e) {
			throw new TokenValidationFailedException("Token Expired");
		}
		return ans;

	}
}
