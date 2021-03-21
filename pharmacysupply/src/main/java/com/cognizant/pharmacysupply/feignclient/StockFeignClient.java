package com.cognizant.pharmacysupply.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cognizant.pharmacysupply.model.MedicineStock;

@FeignClient(url = "http://localhost:8081", name = "medicine-stock-service")
public interface StockFeignClient {
	
	/*
	 * Supply count of a particular medicine with the same name is returned
	 * @param medicine name
	 * @return supply count 
	*/
	@PostMapping("/api/medicine-stock/get-stock-count/{medicine}")
	public MedicineStock getNumberOfTabletsInStockByName(@RequestHeader(name = "Authorization") String token,@PathVariable("medicine") String medicine);
	
	/*
	 * @param medicine 
	 * @param count
	 * @return boolean value to show successful or unsuccessful update 
	*/
	@PostMapping("/api/medicine-stock/update-stock/{medicine}/{count}")
	public Boolean updateNumberOfTabletsInStockByName(@RequestHeader(name = "Authorization") String token,@PathVariable("medicine") String medicine, @PathVariable("count") int count);	
}