package com.cognizant.authorization.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.Before;
import org.junit.Test;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthResponseTest {

	@Mock
	public AuthResponse response;
	
	@Before
	public void setUp() throws Exception{
		response=new AuthResponse();
		response.setUserid("admin");
		response.setUsername("admin");
		response.setValid(false);
	}
		
	@Test
	public void AllArgConstTest() {
		AuthResponse auth=new AuthResponse("admin","admin",false);
		assertEquals(response.getUsername(), auth.getUsername());
		assertEquals("admin", auth.getUserid());
		}
	
	@Test
	public void testToStringMethod() {
	assertEquals("AuthResponse(userid=" + response.getUserid() + ", username=" + response.getUsername()
				+ ", isValid=" + response.isValid() + ")", response.toString());
	}
	@Test
	public void testNoArgsConstructor() {
		
		AuthResponse response = new AuthResponse();
		assertEquals(response, response);
	}
	@Test
	public void testSetters() {
		response.setUsername("admin");
		response.setUserid("admin");
		response.setValid(true);
		assertEquals("admin", response.getUsername());
	}
	
	@Test
	public void testEqualsMethod() {
		boolean equals = response.equals(response);
		assertTrue(equals);
	}

	@Test
	public void testHashCodeMethod() {
		int hashCode = response.hashCode();
		assertEquals(hashCode, response.hashCode());
	}
}
