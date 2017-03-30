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
	    
	    jlTest.setJobCount(18);
	    assertEquals(18, jlTest.getJobCount());
	    
	}
	
	
	
	@Test
	public void testGetSizeOfJobList()
	{
		JobList jlTest = new JobList();
		jlTest.setJobCount(15);
		assertEquals(15, jlTest.getSizeOfJobList());
	}
	
	@Test(expected=NullPointerException.class)
	public void testAddJobs() {
		JobList jlTest = new JobList();
		//Should throw NullPointerException,
		//as setJobCount() was never called.
		jlTest.addJob(new Job());
	}
	
	@Test
	public void testGetJobAtIndex(){
		JobList jlTest = new JobList();
		jlTest.setJobCount(4);
		Job test1 = new Job();
		Job test2 = new Job();
		
		test1.setName("T1");
		test2.setName("T2");
		
		jlTest.addJob(new Job());
		jlTest.addJob(new Job());
		
		assert("T1".equals(jlTest.getJobAtIndex(1).getName()));
		assert("T2".equals(jlTest.getJobAtIndex(2).getName()));
	}

}
