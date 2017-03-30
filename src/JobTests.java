/*
 * Written By: Jaken Herman
 * Date : March 29, 2017
 * File : JobTests.java
 * Contact: JakenHerman7@Gmail.com
 * 
 */

import static org.junit.Assert.*;
import org.junit.Test;

public class JobTests {
	
	@Test
	public void testGetAndSetArrival(){
		Job jTest = new Job();
		assertEquals(0, jTest.getArrival());
		jTest.setArrival(15);
		assertEquals(15, jTest.getArrival());
		
		jTest.setArrival(44);
		assertEquals(44, jTest.getArrival());
	}

	@Test
	public void testGetAndSetBurst(){
		Job jTest = new Job();
		assertEquals(0, jTest.getBurst());
		jTest.setBurst(15);
		assertEquals(15, jTest.getBurst());
		
		jTest.setBurst(44);
		assertEquals(44, jTest.getBurst());
	}
	
	@Test
	public void testGetAndSetName(){
		Job jTest = new Job();
		assert(jTest.getName() == null);
		jTest.setName("T1");
		assert("T1".equals(jTest.getName()));
		
		jTest.setName("T2");
		assert("T2".equals(jTest.getName()));
	}
	
	@Test
	public void testGetAndSetPriority(){
		Job jTest = new Job();
		assertEquals(0, jTest.getPriority());
		jTest.setPriority(15);
		assertEquals(15, jTest.getPriority());
		
		jTest.setPriority(44);
		assertEquals(44, jTest.getPriority());
	}
}
