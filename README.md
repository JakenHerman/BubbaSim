# BubbaSim
A Processor Scheduling Simulator GUI written in Java. This simulator follows a preemptive priority algorithm. 

`.jar` file coming soon.

To Begin
---
 - Clone to your computer with `git clone https://www.github.com/jakenherman/bubbasim.git`.
 - Change directories into the new folder with `cd bubbasim`.
 - Compile all java files: `javac BubbaSimGUI.java && javac PriorityComparator.java && javac Job.java && javac JobList.java && SimulationThread.java`
 - Once all files are compiled, just run `java BubbaSimGui`.

Notes
---
Make sure your input file follows the proper format. The input file (a simple .txt is what we're looking for) will follow the structure of the first line being the amount of jobs to process, followed by one job per line. The jobs should be in the order of arrival time, priority, and CPU burst, separated by whitespace. An example of a proper input file would be:
    
    2
    3 2 5
    4 1 7
    
Another thing important to note is at the current stage of development, the jobs on the input file should be in order as far as arrival time goes. If you do not place the jobs in ascending numerical order for arrival time, you will have a bad time.

For Testing
---
If you'd like to further work on the project on your own and implement testing, there are a few testing files attached. Compile these as you would any normal java file.

Ease of Development
---
I would advise you to use this in Eclipse. I developed the entirety of the program in Eclipse Mars, so should you have any difficulty with another IDE, perhaps try using Eclipse Mars. 
