# CasinoSimulator
The program will run a simulation of various games found within a casino. These include standard games such as Slots, Blackjack, etc. The program will have a main menu to select each game and will include a graphical user interface (GUI) to interact with the various games while playing. There will be a free-play mode, where there is infinite money to be played with, and a standard mode with a limited amount of money to start with.

![This is a screenshot.](casino.png)

# How to run
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
