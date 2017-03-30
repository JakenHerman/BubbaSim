/*
 * Written By: Jaken Herman
 * Date : March 29, 2017
 * File : SimulationThread.java
 * Contact: JakenHerman7@Gmail.com
 * 
 */

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.TextArea;
import java.awt.Choice;

public class BubbaSimGUI implements ActionListener{

	private JFrame frame;
	private int Quantum;
	
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
		
		//Allow the user to select how fast the simulation will run.
		Choice choice = new Choice();
		frame.getContentPane().add(choice, BorderLayout.EAST);
		choice.addItem("Slow");
		choice.addItem("Medium");
		choice.addItem("Fast");

		
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
				
				//Clear JobList Completely in case user wants to run another
				//input file.
				
				/*
				 * In the event of two Jobs having the same priority, we
				 * will need to allow the user to select a quantum for the
				 * Round Robin algorithm. This JOptionPane does that.
				 * 
				 */
				 Quantum = Integer.parseInt(JOptionPane.showInputDialog("In the event of two " +
						 		"jobs having the same priority, please enter " +
						        "the quantum you'd like to use for the Round " +
						 		"Robin algorithm"));
				 log.append("Quantum set to " + Quantum + "\n");
				
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
		            	    
        	    			//Create the JobList that will be processed.
        	    			JobList jl = new JobList();    
		            	    
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
							runSimulation(jl, log, choice);

		            	    
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
	public void runSimulation(JobList jl, TextArea log, Choice choice){

		//Create a thread that will run our simulation
		SimulationThread newSimulation = new SimulationThread();

		//Calculate total burst time among all jobs so we know how long
		//to run the simulation.
		int totalBurstTime = 0;
		for(int i = 0; i < jl.getJobCount(); i++){
			totalBurstTime += jl.getJobAtIndex(i).getBurst();
		}
		
		//Tell our thread the total burst time.
		newSimulation.setTotalBurstTime(totalBurstTime);
		
		//Create Gannt Chart Data Structure in order to generate
		//full chart after processing all jobs.
		String[] chart = new String[totalBurstTime];
		
		//Send our newly created Gannt Chart to our thread.
		newSimulation.setChart(chart);
		
		//Create a comparator for our PriorityQueue.
		Comparator<Job> comparator = new PriorityComparator();
		
		//Create a Priority Queue for our simulator.
		PriorityQueue<Job> jobQueue = new PriorityQueue<Job>(totalBurstTime, comparator);
		
		//Send any other pertinent information to our thread.
		newSimulation.setTextArea(log);
		newSimulation.setJobList(jl);
		newSimulation.setJobQueue(jobQueue);
		newSimulation.setComparator(comparator);
		
		//Set the speed last, depending on user's selection of 'choice'
		if(choice.getSelectedItem().equals("Slow")){
			newSimulation.setSpeed(1000);
		}
		else if(choice.getSelectedItem().equals("Medium")){
			newSimulation.setSpeed(100);
		}
		else {
			newSimulation.setSpeed(1);
		}
		
		//Begin the thread
		newSimulation.start();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// unused actionPerformed.
	}
}
