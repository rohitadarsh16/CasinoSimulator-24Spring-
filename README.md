# Project Name
Put here a short paragraph describing your project. 
Adding an screenshot or a mockup of your application in action would be nice.  

![This is a screenshot.](casino.png)
# How to run
Provide here instructions on how to use your application.   
- Download the latest binary from the Release section on the right on GitHub.  
- On the command line navigate to the **bin** directory and run with
```
java -jar Casino.jar
```
- A window will open displaying an image of Ace of Hearts

# How to contribute
Follow this project board to know the latest status of the project: [Trello](https://trello.com/b/EWAn1oRz/casinosimulator-tasks)

### How to build
- Clone main branch into a folder
- Open IntelliJ 11
- Build the project by going to Build -> Build Project
- Open a terminal and navigate to out/production/CasinoSimulator
- Now run
```
jar cfm Casino.jar MANIFEST.MF CasinoSimulator.class
```
- Move **Casino.jar** to the bin directory
- Navigate to the **bin** directory and run
```
java -jar Casino.jar
```
- Have fun!!!
