public class Job {

    private int arrival;
    private int burst;
    private int priority;
    private String job_name;

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
