import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.TextArea;

public class BubbaSimGUI implements ActionListener{

	private JFrame frame;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BubbaSimGUI window = new BubbaSimGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BubbaSimGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		

		//Create an array to auto-assign job names
		//as they come in to be processed.
		String[] jobNames = new String[26];
		
		//Create the JobList that will be processed.
		JobList jl = new JobList();
		
		//Fill jobNames array with the alphabet
		for(int i=65;i<=90;i++) {
			jobNames[i-65]= Character.toString((char)i);
		} 
		
		//initialize JFrame
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create the button that will allow the user to select a
		//particular file to load.
		JButton btnLoadFile = new JButton("Load File");
		frame.getContentPane().add(btnLoadFile, BorderLayout.SOUTH);
		
		//Create a "console" output area so user can see what is
		//going on in the program at all times.
		TextArea log = new TextArea();
		log.setColumns(2);
		frame.getContentPane().add(log, BorderLayout.CENTER);
		
		//Create the button that will allow the user to run the
		//file loaded to the program earlier. This will read the
		//file and begin processing the data.
		JButton btnRunSimulation = new JButton("Run Simulation");
		frame.getContentPane().add(btnRunSimulation, BorderLayout.NORTH);

		
		//Create a file chooser
		final JFileChooser fc = new JFileChooser();
	
		//In response to load button click, browse for file
		btnLoadFile.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        //Open system navigator
		    	int returnVal = fc.showOpenDialog(fc);
		    	//Check if a file was loaded
		    	if (returnVal == JFileChooser.APPROVE_OPTION) {
		             File file = fc.getSelectedFile();
		             //Tell the user we opened the file
		             log.append(file.getName() + " has been loaded to the system.\n");
		         } else {
		        	 //If the user doesn't select a file, tell them.
		             log.append("Open file command cancelled by user.\n");
		         }
		    }
		}); 
		
		//In response to process button click, process the file
		btnRunSimulation.addActionListener( new ActionListener()
		{
			/*
			 * If jobCountFlag reaches 1, it means we have already
			 * processed the first line from the input file, which
			 * we know is not a job. The first line from the input
			 * file will always be the amount of jobs to be 
			 * processed. 
			 */
		    public void actionPerformed(ActionEvent e)
		    {
				int jobCountFlag = 0;
				int errorFlag = 0;
				
				//jobs_counted is a way to keep track of how many 
				//jobs have already been read so we can assign job
				//names as they come in.
				int jobs_counted = 0;
				
		        File file = fc.getSelectedFile();
		    	//Check if file exists in from our JFileChooser
		        //Warn the user if there is no file.
		        if (file == null){
		        	log.append("There is no loaded file. Press the " +
		                       "'Load File' button before attempting " +
		        			   "to process, please.\n");
		        }
		        //If there is a file, process it.
		        else {
		             //Tell the user we opened the file
		             log.append(file.getName() + " is being processed.\n");
		             //Read the File line by line
		             try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		            	    String line; // where we will store each line from input file.
		            	    //While there are more lines in the file, process them
		            	    while ((line = br.readLine()) != null) {
		            	       // Check if first line has been processed
		            	    	if(jobCountFlag == 0){
		            	    		//Ensure the first line of the file is an integer.
		            	    		int jobCount = 0;
		            	    		try {
		            	    			//If the first line is has integer value, go ahead
		            	    			//and parse to an integer.
		            	    			jobCount = Integer.parseInt(line);
			            	    		
			            	    		//Set the jobCount variable in our 
			            	    		//JobList to be the value in the first line of our
			            	    		//input file.
			            	    		jl.setJobCount(jobCount);
			            	    		
			            	    		//Tell the program we've processed the first line.
			            	    		jobCountFlag = 1;
			            	    		
			            	    		//Tell the user how many Jobs will be processed.
			            	    		log.append("Total number of Jobs to Process: " +
			            	    		           jl.getJobCount() + "\n");	
			            	    		
		            	    		} catch (NumberFormatException e1) {
		            	    			//If the first line does not have integer value, 
		            	    			//throw error.
		            	    			if (errorFlag == 0){
		            	    				log.append("Invalid input file selected\n");
		            	    				errorFlag = 1;
		            	    			}
		            	    		}

		            	    	}
		            	    	// If first line has already been processed, begin adding
		            	    	// Jobs to the JobList.
		            	    	else {
		            	    		//Split the Job input data on all instances of whitespace
		            	    		String[] jobAspects = line.split(" ");
		            	    		//Create a new Job Object to add to our JobList per input line
		            	    		Job job = new Job();
		            	    		//Set all aspects of our new job (Arrival, Priority, Burst, and
		            	    		//Name.
		            	    		job.setArrival(Integer.parseInt(jobAspects[0]));
		            	    		job.setPriority(Integer.parseInt(jobAspects[1]));
		            	    		job.setBurst(Integer.parseInt(jobAspects[2]));
		            	    		job.setName(jobNames[jobs_counted]);
		            	    		
		            	    		//Add the job to the JobList
		            	    		jl.addJob(job);
		            	    		
		            	    		//Finally, increment jobs_counted. Note, we can process a maximum of
		            	    		//26 jobs.
		            	    		jobs_counted++;
		            	    	}
		            	    }
		            	    
		            	    //Begin to run simulation
		            	    try {
								runSimulation(jl, log);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
		            	    
		            	} catch (IOException e1) {
							// Print any exceptions
							e1.printStackTrace();
						}
		        }
		    }
		}); 
	
	}
	
	/* runSimulation method is what actually gets displayed to the user.
	 * After clicking "Run Simulation" button, the program just loads the
	 * jobs. runSimulation() actually processes the information the jobs
	 * hold.
	 */
	public void runSimulation(JobList jl, TextArea log) throws InterruptedException {
		
		//Instantiate a comparator for priority comparison
		//among received Jobs to process.
        Comparator<Job> comparator = new PriorityComparator();
        
        //Initialize a Priority Queue that the jobs will be added 
        //to, so we ensure that top-priority jobs run first.
		PriorityQueue<Job> jobQueue = 
	            new PriorityQueue<Job>(jl.getJobCount(), comparator);
		
		//Calculate total burst time among all jobs so we know how long
		//to run the simulation.
		int totalBurstTime = 0;
		for(int i = 0; i < jl.getJobCount(); i++){
			totalBurstTime += jl.getJobAtIndex(i).getBurst();
		}
		
		//Create Gannt Chart Data Structure in order to generate
		//full chart after processing all jobs.
		String[] chart = new String[totalBurstTime];
		
		//Run the simulation from 0 to the end of all job CPU bursts.
		for(int i = 0; i < totalBurstTime; i++){
			log.append("CPU Timestap: " + Integer.toString(i) + "\n");
			
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
				ganntChartGenerator(chart, "|"+jobQueue.peek().getName(), i);
				//Decrement the amount of remaining burst time the Job should
				//have.
				jobQueue.peek().setBurst(jobQueue.peek().getBurst()-1);
				
				//If the peeking Job has no more CPU Burst remaining, we remove
				//the Job from the Job Queue.
				if (jobQueue.peek().getBurst() == 0){
					jobQueue.remove();
				}
			}
		}
		//Display the Gannt Chart of the Simulation
		displayGanntChart(chart, log);
	}
	
	//Allow the user to display the Gannt chart created by running the 
	//simulation.
	public void displayGanntChart(String[] chart, TextArea log){
		for(int i = 0; i < chart.length; i++){
			log.append(chart[i]);
		}
	}
	
	public void ganntChartGenerator(String[] chart, 
									String processName, int index){
		chart[index] = processName;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//necessary for ActionListener, not used.
	}

}
