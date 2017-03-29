
public class JobList {
	private int jobCount;
	private Job[] jobs;
	private int jobIndex = 0;
	
	//Allows us to set the number of jobs
	//that will be in our JobList.
	public void setJobCount(int count){
		jobCount = count;
		initializeJobs();
	}
	
	//Allows us to initialize and allocate
	//space for the jobs array
	private void initializeJobs(){
		jobs = new Job[jobCount];
	}
	
	//Allows us to add jobs to the JobList.
	public void addJob(Job newJob){
		jobs[jobIndex] = newJob;
		jobIndex++;
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
}