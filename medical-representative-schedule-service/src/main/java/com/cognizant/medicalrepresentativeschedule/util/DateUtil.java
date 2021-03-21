package com.cognizant.medicalrepresentativeschedule.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cognizant.medicalrepresentativeschedule.exception.TokenValidationFailedException;
import com.cognizant.medicalrepresentativeschedule.feignclient.AuthenticationFeignClient;
import com.cognizant.medicalrepresentativeschedule.model.JwtResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtil {

	public static LocalDate getDate(String scheduleStartDate) {

		LocalDate localDate = null;
		try {

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
			localDate = LocalDate.parse(scheduleStartDate, formatter);

			log.debug("date : {}", localDate);

		} catch (Exception e) {
			log.debug("Date Format Exception");
		}

		return localDate;
	}
	
}
