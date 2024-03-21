# CasinoSimulator
The program will run a simulation of various games found within a casino. These include standard games such as Slots, Blackjack, etc. The program will have a main menu to select each game and will include a graphical user interface (GUI) to interact with the various games while playing. There will be a free-play mode, where there is infinite money to be played with, and a standard mode with a limited amount of money to start with.

![image](https://user-images.githubusercontent.com/77748455/163686845-8bcf28f7-f819-483e-8f21-ed26a892fb88.png)

#IMPORTANT!
Minimum Java version required is **17.0.1**.

# How to run
- Download Casino.zip from the latest release
- Extract the zip file; there should be a **jar** file and **Assets** and **Sounds** folders
- On the command line navigate to the folder where the files were extracted and run
```
java -jar CasinoSimulator.jar
```
- A window will open with a menu where one can choose which game to play

# How to contribute
Follow this project board to know the latest status of the project: [Trello](https://trello.com/b/EWAn1oRz/casinosimulator-tasks)

### How to build
- Clone main branch into a folder
- Open IntelliJ 11
- Build the project by going to Build -> Build Project
- Now go to File -> Project Structure and then click on Artifacts
- If this is empty click on the + sign and choose JAR -> From modules with dependencies
- Under Main Class select CasinoSimulator then click OK and click OK again
- Now click on Build -> Build Artifacts
- A new folder named **artifacts** will be created under the **out** folder
- Navigate to the last folder and move **CasinoSimulator.jar** to the main directory where the Assets folder is also located
- In the terminal navigate to the main folder and run
```
java -jar CasinoSimulator.jar
```
- Have fun!!!

### Testing
Testing was perfomed using JUnit testing suite primarily on macOS. There are two classes meant for this purpose: SlotModelTest.java and BlackjackTest.java.
**Warning** Some of the tests behave differently if ran on macOS or Windows. For example, when automating clicking on a bet label, say the $5 label, macOS requires two
clicks in order to register one click whereas on Windows only one click is sufficient. This means that one pair of mousePress, mouseRelease functions need to be commented
out when testing on Windows.

### Feature List
1. We implemented a Temple Alert's feature where the user can see the latest post made on www.reddit.com/r/Temple/. The feature works when the user clicks on the Temple News image and can click the exit button to return back to the main menu.
2. We also implemented a Random Number Game where the user can guess a number between 1-100 and make a bet on their guess. They have multiple chances, getting told their guess is too low, too high, or the correct number. They must guess the correct number to win their bet. 
