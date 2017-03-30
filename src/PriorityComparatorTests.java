import static org.junit.Assert.*;
import java.util.Comparator;
import org.junit.Test;

/*
 * Written By: Jaken Herman
 * Date : March 29, 2017
 * File : PriorityComparatorTests.java
 * Contact: JakenHerman7@Gmail.com
 * 
 */


public class PriorityComparatorTests {

	@Test
	public void testCompare(){
		
		Comparator<Job> testComparator = new PriorityComparator();
		
		Job T1 = new Job();
		Job T2 = new Job();
		
		T1.setPriority(1);
		T2.setPriority(2);
		
		assertEquals(-1, testComparator.compare(T1, T2));
		
		T1.setPriority(2);
		T2.setPriority(1);
		
		assertEquals(1, testComparator.compare(T1, T2));
		
		T1.setPriority(1);
		T2.setPriority(1);
		
		assertEquals(0, testComparator.compare(T1, T2));

	}
}
