/*
 * Written By: Jaken Herman
 * Date : March 29, 2017
 * File : SimulationThread.java
 * Contact: JakenHerman7@Gmail.com
 * 
 */

import java.util.Comparator;

public class PriorityComparator implements Comparator<Job>{
	
	@Override
	public int compare(Job o1, Job o2) {
		
		if (o1.getPriority() < o2.getPriority())
		{
			return -1;
		}
		
		if (o1.getPriority() > o2.getPriority())
		{
			return 1;
		}
		
		return 0;
	}
}