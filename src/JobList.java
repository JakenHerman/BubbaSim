/*
 * Written By: Jaken Herman
 * Date : March 29, 2017
 * File : JobList.java
 * Contact: JakenHerman7@Gmail.com
 * 
 */

public class JobList {
	private int jobCount;
	private Job[] jobs;
	private int jobIndex = 0;
	private int earliestArrival;
	
	//Allows us to set the number of jobs
	//that will be in our JobList.
	public void setJobCount(int count){
		jobCount = count;
		initializeJobs();
	}
	
	//Set which process begins first in order
	//to ensure our CPU runs through entire burst.
	public void setEarliestArrival(){
		earliestArrival = jobs[0].getArrival();
		for(int i = 0; i < jobCount; i++){
			if (jobs[i].getArrival() < this.earliestArrival){
				this.earliestArrival = jobs[i].getArrival();
			}
		}
	}
	
	//Allow the user to obtain which process will
	//arrive first.
	public int getEarliestArrival(){
		return this.earliestArrival;
	}
	
	//Allows us to initialize and allocate
	//space for the jobs array
	private void initializeJobs(){
		jobs = new Job[jobCount];
	}
	
	//Allows us to add jobs to the JobList.
	public void addJob(Job newJob){
		jobs[jobIndex] = newJob;
		jobIndex += 1;
	}
	
	//Allow user to check the amount of Jobs
	//the program will process, for verification.
	public int getJobCount(){
		return jobCount;
	}
	
	//Allows user to double-verify the amount 
	//of jobs being processed by returning the
	//exact length of the JobList Object.
	//This method is for UnitTesting.
	public int getSizeOfJobList(){
		return jobs.length;
	}
	
	//Allow user to peek at a specific job in
	//the JobList.
	public Job getJobAtIndex(int index){
		return jobs[index];
	}
}
