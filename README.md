# CCT-2016-Christmas-Project
This is a program I made for my Christmas project at the College of Computing Technology. The program is a bi dimensional robot with unit collision and unit detection. 

My code does the following:

File "Robot.java" takes care of all the physics related to the robots.That is: takes care of drawing the robots, rotating the robots, acceleration, position, orientation and information related to the robots.

File "Interface.java" is basically just a canvas for the interface system, it simply draws a rectangle to serve as a base for where the buttons and information will be displayed.

File "Button.java" takes care of drawing rectangles that can be used both as buttons or as information displays. In the current build we have 3 information displays that show all the information regarding the robots, 1 Detail button that shows the distance between the robots and 1 Collision counter that keeps track of how many times the robots have collided.

File "UseRobot.java" is where our main method is located and where we put everything together. In this file we also have a method that keeps track of the location of the mouse and provides some extra details when the mouse hovers the DETAIL button. 

/////////////////////////////////////////////////////////

To compile my code:

This program requires the core.jar Processing Library. If you do not have this Library, please take the following steps, otherwise, skip to step 8:

1 Navigate to the website https://processing.org/
2 In the https://processing.org/ website, find the download section on the left side of the page.
3 Click on the "No Donation" button if you do not wish to donate and then click on the "Download" button.
4 Once you are redirected to the next page, select the Operating system version you are currently using and wait for the download to start.
5 Once the download is finished, open the zip file using winrar, 7rar, winzip or any other program you prefer.
6 Extract the contents of the rar file into a new folder.
7 Once you have extracter navigate to FolderYouCreated/core/library and find the core.jar file.
8 Copy the core.jar file to the same folder that you extracted the other files (Robot.java, UseRobot.java, etc...)
9 You should now have a folder with 6 files (Robot.java, UseRobot.java, Button.java, Interface.java, README.txt and core.jar)
10 Open your "Run" window by holding down the Windows Key on your keyboard then pressing the R key.
11 Once the "Run" window is open, type cmd and press the ENTER key.
12 On the black window, please use the command cd (Change directory) to navigate to the folder where the 6 files are locates.
I.E: cd C:\Users\USERNAME\Desktop\FOLDERNAME
13 Once you have navigated to the folder, insert the following command on the command prompt to compile the code:
javac -cp ".;core.jar" UseRobot.java


To run my code:

1 Once the code has been properly compiled, open your "Run" window by holding down the Windows Key on your keyboard then pressing the R key.
2 Once the "Run" window is open, type cmd and press the ENTER key.
3 On the black window, please use the command cd (Change directory) to navigate to the folder where the 6 files are locates.
I.E: cd C:\Users\USERNAME\Desktop\FOLDERNAME
4 Once you have navigated to the folder, insert the following command on the command prompt to run the code:
java -cp ".;core.jar" UseRobot


///////////////////////////////////////////////////////////////////////

I made this project during the month of December of 2016. I think there is a lot of room for improvement but back then I didn't have a lot of knowledge about inheritance, polymorphism and other concepts that would have helped me a lot during the project. I am overall very satisfied with the results I got with the knowledge I had at the time since this project received a 96% score and was the highest graded project in the first year.
