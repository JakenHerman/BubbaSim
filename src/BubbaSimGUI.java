import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
	private JTextField textField;

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
		             log.append(file.getName() + "has been loaded to the system.\n");
		         } else {
		        	 //If the user doesn't select a file, tell them.
		             log.append("Open file command cancelled by user.\n");
		         }
		    }
		}); 
		
		//In response to process button click, process the file
		btnRunSimulation.addActionListener( new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
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
		             log.append(file.getName() + "has been processed.\n");
		        }
		    }
		}); 
	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//necessary for ActionListener, not used.
	}


}
