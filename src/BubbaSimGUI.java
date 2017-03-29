import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
		
		JobList jl = new JobList();
		
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
		            	    		log.append("another job: " + line + "\n");
		            	    	}
		            	    }
		            	} catch (IOException e1) {
							// Print any exceptions
							e1.printStackTrace();
						}
		        }
		    }
		}); 
	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//necessary for ActionListener, not used.
	}


}
