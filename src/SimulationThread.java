/*
 * Written By: Jaken Herman
 * Date : March 29, 2017
 * File : SimulationThread.java
 * Contact: JakenHerman7@Gmail.com
 * 
 */

import java.awt.TextArea;
import java.util.Comparator;
import java.util.PriorityQueue;


public class SimulationThread extends Thread {

	//The TextArea of our application will be loaded so we
	//can display the output to the user
	TextArea log;
	
	//Create a Turnaround instance in order to calculate and log
	//turnarounds of all jobs processed.
	Turnaround turnarounds = new Turnaround();
	
	//The totalBurstTime so we know how many times we need to process
	//jobs in our JobList.
	int totalBurstTime;
	
	//The speed will change depending on what speed the user selects.
	//This will make the simulation run slow, medium, or fast.
	int speed;
	
	//Our JobList that we loaded in BubbaSimGUI.btnRunSimulation().
	JobList jl;
	
    //Create a Priority Queue that the jobs will be added 
    //to, so we ensure that top-priority jobs run first.
	PriorityQueue<Job> jobQueue;
	
	//Gannt Chart
	String[] chart;
	
	//Create a comparator for priority comparison
	//among received Jobs to process.
	Comparator<Job> comparator;
	
	//Allows the user to specify how fast they want the
	//simulation to run.
	public void setSpeed(int speed){
		this.speed = speed;
	}
	
	//Allows the user to set a custom Comparator.
	public void setComparator(Comparator<Job> comparator){
		this.comparator = comparator;
	}
	
	//Allows the user to set the specifications for our 
	//Gannt Chart.
	public void setChart(String[] chart){
		this.chart = chart;
	}
	
	//Allows the user to set the JobQueue specifications.
	public void setJobQueue(PriorityQueue<Job> jobQueue){
		this.jobQueue = jobQueue;
	}
	
	//Allows the user to set the total burst time after
	//Calculating it in BubbaSimGui.
	public void setTotalBurstTime(int bt){
		this.totalBurstTime = bt;
	}
	
	//Allows the user to specify which TextArea the simulation
	//output will be shown in.
	public void setTextArea(TextArea log){
		this.log = log;
	}
	
	//Allows the user to specify the JobList we will process.
	public void setJobList(JobList jl){
		this.jl = jl;
	}
	
    public void run() {
    	
    	
    	//Tell our turnaround log how many jobs it will need to log.
    	int total_jobs = jl.getJobCount();
    	turnarounds.setTotalJobs(total_jobs);
    	
    	//Set the earliest arrival so we know how long to burst.
    	jl.setEarliestArrival();
    	
    	//Set new burst time according to totalBurstTime + earliest arrival
    	//to ensure that ghost bursts don't prematurely end program.
    	int CPUBurstTime = totalBurstTime + jl.getEarliestArrival();
    	
		//Run the simulation from 0 to the end of all job CPU bursts.
		for(int i = 0; i < CPUBurstTime; i++){
			//Use sleep() function in order to slow down simulation
			//depending on this.speed.
			try {
				Thread.sleep(this.speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			log.append("CPU Timestamp: " + Integer.toString(i) + "\n");
			
			//For every job in the JobList jl, check to see if any Jobs
			//are arriving at the current CPU timestamp
			for(int k = 0; k < jl.getJobCount(); k++){
				Job currentJob = jl.getJobAtIndex(k);

				if(currentJob.getArrival() == i){
					//If Job arrives at timestamp i, append it's name to the log.
					log.append("Job " + currentJob.getName() + " arrived. \n");
					
					//Add the Job to the PriorityQueue.
					jobQueue.add(currentJob);
				}
			}
			
			//If there is nothing in the job Queue, there is nothing
			//to display on the Gannt Chart, hence '#' is entered.
			if(jobQueue.isEmpty()){
				ganntChartGenerator(chart, "|#", i);
			}
			//If the Job Queue does have jobs available, peek the 
			//outgoing job into the Gannt Chart, then remove the 
			//Job from the job Queue.
			else { 
				log.append("Running Process " + jobQueue.peek().getName() + "\n");
				ganntChartGenerator(chart, "|" + jobQueue.peek().getName(), i);
				//Decrement the amount of remaining burst time the Job should
				//have.
				jobQueue.peek().setBurst(jobQueue.peek().getBurst()-1);
				
				//If the peeking Job has no more CPU Burst remaining, we remove
				//the Job from the Job Queue.
				if (jobQueue.peek().getBurst() == 0){
					//Set the exit time for the job in order to calculate
					//turnaround time of job.
					jobQueue.peek().setExit(i);
					
					//Calculate Turnaround
					Job currentJob = jobQueue.peek();
					int turnaround = currentJob.getExit() - currentJob.getArrival();
					String turnaroundLog = "Process " + currentJob.getName() + " ended at time "
										   + i + ", with Turnaround time of: " + turnaround + "\n";
					turnarounds.addTurnaround(turnaroundLog);
					
					//Remove Job from Job Queue
					jobQueue.remove();
				}
			}
		}
		
		//Interrupt the thread and return to the GUI after simulation
		//is completed.
		Thread.currentThread().interrupt();
		
		//Display final turnaround log.
		turnarounds.listTurnarounds(log);
		
		//Display the final Gannt Chart.
		displayGanntChart(chart, log);
		
    }
    
	//Allow the user to display the Gannt chart created by running the 
	//simulation.
	public void displayGanntChart(String[] chart, TextArea log){
		for(int i = 0; i < chart.length; i++){
			log.append(chart[i]);
		}
	}
	
    //Creates a Gannt Chart of the current simulation to be printed at the
    //end of processing all Jobs.
	public void ganntChartGenerator(String[] chart, 
									String processName, int index){
		chart[index] = processName;			
	}
	
    public static void main(String args[]) {
        (new SimulationThread()).start();
    }

}