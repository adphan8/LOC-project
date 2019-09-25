                #Duduva tool

##I.	Functionality of tool
------------------------------------
This tool is used to

- Count the number of lines of code and the number of comment line each file until a specified date
- Show the changes in the number of lines of code made by different users to each file during a specified period of time
- Show the changes in status of the file during that period of time (whether the file is added, deleted or modified or none)

##II.	Requirement
----------------------
- Available platform: Any computer with **Windows** or **MacOS** operating system
- Used only for **Git** projects
- Required **Internet** connected because this tool is built in _Maven_ Project 

##III.	Input
------------------
This tool requires a **CSV input file**. Each line of the CSV file is the information of the project that needs to be checked. Each line needs to include _5_ _types_ of information, separated by a _comma_ (,) in the order as followed

1. Gitlab **username**
2. Gitlab **user password**
3. Gitlab **URL of the project**
4. **Start date** of the period you want to check in this format **MM/DD/YYYY** where MM,DD are maximum _two-digit_ number. YYYY is a _four-digit_ number
5. **End date** of the period you want to check, also in the _similar format_ as the start date


##IV.	Output
-----------------------
The tool outputs to an **excel file** where it shows different **sheets** according to the name of the projects. Each sheet displays _two_ _tables_:
 • **Project input information table**
1. 	Project’s name
2. 	The time interval
 • **Project change in detail table** 
1.	File name present in the project	
2.	Number of comments
3.	Number of lines (not counting blank line)
4.	Percentage of number of comments out of total number of lines in the file    
5.	Authors contributed to the file
6.	Email of authors
7.	LOC Added shows the number of lines of code added by each author
8.	LOC Deleted shows the number of lines of code deleted by each author
9.	LOC Changed shows the total  number of lines of code modified by each author (= add+delete)
10.	File Added, there will be a tick if the file is added during that period
11.	File Deleted , there will be a tick if the file is deleted during that period
12.	File Modified , there will be a tick if the file is modified during that period
