/*
 * 
 * White-box Unit Testing for JobList Object
 * Written By: Jaken Herman
 * Year : 2017
 * 
 */

import static org.junit.Assert.*;
import org.junit.Test;

public class JobListTests {

	/*Ensure that when we set the number of Jobs using
	 * setJobCount(), JobListInstance.jobCount is equal
	 * to the parameter passed in setJobCount(). 
	 * 
	 * Simultaneously, we are testing getJobCount(), as 
	 * the jobCount variable is private so we are testing
	 * our getter method as well.
	 */
	
	@Test
	public void testJobCountSetterAndGetter() 
	{
	    JobList jlTest = new JobList();
	    jlTest.setJobCount(5);
	    assertEquals(5, jlTest.getJobCount());
	}
	
	@Test
	public void testGetSizeOfJobList()
	{
		JobList jlTest = new JobList();
		jlTest.setJobCount(15);
		assertEquals(15, jlTest.getSizeOfJobList());
	}
}
