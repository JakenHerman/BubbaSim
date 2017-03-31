import java.awt.TextArea;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Turnaround {
	int totalJobs = 0;
	String[] jobTurnaroundList;
	int turnaroundCount = 0;
	
	public void setTotalJobs(int jobCount){
		this.totalJobs = jobCount;
		initializeJobTurnaroundList();
	}
	
	public void initializeJobTurnaroundList(){
		this.jobTurnaroundList = new String[this.totalJobs];
	}
	
	public void addTurnaround(String turnaround){
		jobTurnaroundList[turnaroundCount] = turnaround;
		turnaroundCount += 1;
	}
	
	public List<String> sortTurnarounds(){
		List<String> jobTurnarounds = Arrays.stream(jobTurnaroundList)
										.collect(Collectors.toList());
		jobTurnarounds.sort(String::compareToIgnoreCase);
		return jobTurnarounds;
	}
	
	public void listTurnarounds(TextArea log){
		List<String> finalList = sortTurnarounds();
		for(int i = 0; i < this.totalJobs; i++){
			log.append(finalList.get(i));
		}
	}
}
