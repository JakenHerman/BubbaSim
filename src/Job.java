/*
 * Written By: Jaken Herman
 * Date : March 29, 2017
 * File : SimulationThread.java
 * Contact: JakenHerman7@Gmail.com
 * 
 */

public class Job {

    private int arrival;
    private int burst;
    private int priority;
    private String job_name;
    private int exit;

    public int getArrival(){
        return this.arrival;
    }

    public int getBurst(){
        return this.burst;
    } 

    public int getPriority(){
        return this.priority;
    }

    public String getName(){
        return this.job_name;
    }
    
    public void setExit(int exit_received){
    	this.exit = exit_received;
    }
    
    public int getExit(){
    	return this.exit;
    }

    public void setArrival(int arrival_received){
        this.arrival = arrival_received;
    }

    public void setBurst(int burst_received){
        this.burst = burst_received;
    }

    public void setPriority(int priority_received){
        this.priority = priority_received;
    }

    public void setName(String name_received){
        this.job_name = name_received;
    }

}
