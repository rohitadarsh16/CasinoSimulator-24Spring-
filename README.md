# CasinoSimulator
The program will run a simulation of various games found within a casino. These include standard games such as Slots, Blackjack, etc. The program will have a main menu to select each game and will include a graphical user interface (GUI) to interact with the various games while playing. There will be a free-play mode, where there is infinite money to be played with, and a standard mode with a limited amount of money to start with.

![This is a screenshot.](casino.png)

# How to run
- Download Casino.jar and the Assets folder from the latest release
- On the command line navigate to the folder where the files were downloaded and run
```
java -jar Casino.jar
```
- A window will open with a menu where one can choose which game to play

# How to contribute
Follow this project board to know the latest status of the project: [Trello](https://trello.com/b/EWAn1oRz/casinosimulator-tasks)

### How to build
- Clone main branch into a folder
- Open IntelliJ 11
- Build the project by going to Build -> Build Project
- Copy the MANIFEST.MF file to out/production/CasinoSimulator
- Open a terminal and navigate to out/production/CasinoSimulator
- Now run
```
jar cfm Casino.jar MANIFEST.MF *.class
```
- Move **Casino.jar** to the main directory where the Assets folder is also located
- In the terminal navigate to the main folder and run
```
java -jar Casino.jar
```
- Have fun!!!
