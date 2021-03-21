package com.cognizant.authorization.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomErrorResponseTest {
	@Mock
	private CustomErrorResponse customErrorResponse;

	/**
	 * Before running test cases this method should run to set the value for fields
	 */
	@Before
	public void setup() {
		
		customErrorResponse = new CustomErrorResponse();
		customErrorResponse.setStatus(HttpStatus.OK);
		customErrorResponse.setReason("Bad request");
		customErrorResponse.setMessage("Please provide valid value");
		
	}

	/**
	 * @throws Exception test the CustomError model class getter
	 */
	@Test
	public void testMedicineStockDetails() throws Exception {
	
		assertEquals(HttpStatus.OK, customErrorResponse.getStatus());
		assertEquals("Bad request", customErrorResponse.getReason());
		assertEquals("Please provide valid value", customErrorResponse.getMessage());
		

	}

	/**
	 * tests the AllArgsconstructor
	 */
	@Test
	public void testAllArgsConstructor() {
		CustomErrorResponse customErrorResponse = new CustomErrorResponse(null, HttpStatus.OK, "Bad request",
				"Please provide valid value");
		assertEquals("Bad request", customErrorResponse.getReason());
	}

	@Test
	public void testToStringMethod() {
		
		assertEquals(customErrorResponse.toString(), customErrorResponse.toString());
		
	}

	@Test
	public void testEqualsMethod() {
		boolean equals = customErrorResponse.equals(customErrorResponse);
		assertTrue(equals);
	}

	@Test
	public void testHashCodeMethod() {
		int hashCode = customErrorResponse.hashCode();
		assertEquals(hashCode, customErrorResponse.hashCode());
	}

	@Test
	public void testSetterMethod() {
		customErrorResponse.setMessage("Hello");
		customErrorResponse.setReason("BAD REQUEST");
		customErrorResponse.setStatus(HttpStatus.OK);
		customErrorResponse.setTimestamp(null);
		assertEquals("Hello", customErrorResponse.getMessage());
	}

}
