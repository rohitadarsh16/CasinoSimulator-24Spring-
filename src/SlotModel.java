import java.util.Random;

/**
 * SlotModel class for slot-machine. Should contain all the backend methods/functions.
 */
public class SlotModel {
    private MainMenuModel menuModel;
    private SlotView slotView;
    private int money;

    private int[] slot = new int[9];
    private int bettingMoney = 1;

    /**
     * SlotModel constructors for slot-machine. Have menuModel, money, and slotView variable.
     *
     * @param menu  contain main menu model.
     * @param money contain money share across all games and menu.
     */
    public SlotModel(MainMenuModel menu, int money) {
        menuModel = menu;
        this.money = money;
        slotView = new SlotView(this);
    }

    /**
     * Get money method
     *
     * @return money
     */
    public int getMoney() {
        return money;
    }

    /**
     * pull lever method for pull lever button. This method will generate random number from 1 to 7,9, or 11 depend on the
     * difficulty for total of 9 times for 9 slots.
     */
    public void pullLever() {
        Random randomNumber = new Random();
        if (MainMenuView.difficulty == "Hard") {
            for (int i = 0; i < 9; i++) {
                slot[i] = randomNumber.nextInt(11);
            }
        } else if (MainMenuView.difficulty == "Medium") {
            for (int i = 0; i < 9; i++) {
                int val = randomNumber.nextInt(9);
                int val2 = randomNumber.nextInt(8);
                if (val2 == 7) //Double the chance to get a "7" icon
                    val = 1;
                slot[i] = val;
            }
        } else {//Difficuly is set to easy...
            for (int i = 0; i < 9; i++) {
                int val = randomNumber.nextInt(7);
                int val2 = randomNumber.nextInt(9);
                if (val2 >= 7) //Triple the chance to get a "7" icon
                    val = 1;
                slot[i] = val;
            }

        }
    }

    public int Leverdifficulty() {
        int totalRandom;
        if (MainMenuView.difficulty == "Hard") {
            totalRandom = 11;
        } else if (MainMenuView.difficulty == "Medium") {
            totalRandom = 9;
        } else {
            totalRandom = 7;
        }
        return totalRandom;
    }

    /**
     * get betting money
     *
     * @return betting money
     */
    public int getBettingMoney() {
        return bettingMoney;
    }

    /**
     * setSlot method for testing purpose only
     *
     * @param slotNumber set slot position to test
     * @param slotSymbol set slot symbol to test
     */
    public void setSlot(int slotNumber, int slotSymbol) {
        slot[slotNumber] = slotSymbol;
    }

    /**
     * get slot method
     *
     * @param a to get slot's number/position
     * @return that slot's number/position
     */
    public int getSlot(int a) {
        return slot[a];
    }

    /**
     * set betting money for combo-box button options.
     *
     * @param a set "a" to "betting money".
     */
    public void setBettingMoney(int a) {
        bettingMoney = a;
    }

    /**
     * rules for slots. If statement to check if there are any matches symbols in slots.
     *
     * @return winning a string to tell the user is losing or winning. Also, calculate the
     * winning money for the user.
     */
    public String matchCheck() {
        int winningMoney = 0;
        boolean checkIfWon = false;
        if (MainMenuView.gamemode == "Simulated Casino") {
            //first row
            if (slot[0] == slot[1] && slot[1] == slot[2]) {
                if (slot[0] == 1 || slot[0] == 10) {
                    winningMoney = bettingMoney * 5;
                } else {
                    winningMoney = bettingMoney * 2;
                }
            }
            //second row
            if (slot[3] == slot[4] && slot[4] == slot[5]) {
                if (slot[3] == 1 || slot[3] == 10) {
                    winningMoney = bettingMoney * 5;
                } else {
                    winningMoney = bettingMoney * 2;
                }
            }
            //third row
            if (slot[6] == slot[7] && slot[7] == slot[8]) {
                if (slot[6] == 1 || slot[6] == 10) {
                    winningMoney = bettingMoney * 5;
                } else {
                    winningMoney = bettingMoney * 2;
                }
            }
            //first column
            if (slot[0] == slot[3] && slot[3] == slot[6]) {
                if (slot[0] == 1 || slot[0] == 10) {
                    winningMoney = bettingMoney * 5;
                } else {
                    winningMoney = bettingMoney * 2;
                }
            }
            //second column
            if (slot[1] == slot[4] && slot[4] == slot[7]) {
                if (slot[1] == 1 || slot[1] == 10) {
                    winningMoney = bettingMoney * 5;
                } else {
                    winningMoney = bettingMoney * 2;
                }
            }
            //third column
            if (slot[2] == slot[5] && slot[5] == slot[8]) {
                if (slot[2] == 1 || slot[2] == 10) {
                    winningMoney = bettingMoney * 5;
                } else {
                    winningMoney = bettingMoney * 2;
                }
            }
            //top left to bottom right diagonal
            if (slot[0] == slot[4] && slot[4] == slot[8]) {
                if (slot[0] == 1 || slot[0] == 10) {
                    winningMoney = bettingMoney * 5;
                } else {
                    winningMoney = bettingMoney * 2;
                }
            }
            //bottom left to top right diagonal
            if (slot[6] == slot[4] && slot[4] == slot[2]) {
                if (slot[6] == 1 || slot[6] == 10) {
                    winningMoney = bettingMoney * 5;
                } else {
                    winningMoney = bettingMoney * 2;
                }
            }
        } else { //If the gamemode is set to freeplay
            //first row
            if (slot[0] == slot[1] && slot[1] == slot[2]) {
                checkIfWon = true;
            }
            //second row
            if (slot[3] == slot[4] && slot[4] == slot[5]) {
                checkIfWon = true;
            }
            //third row
            if (slot[6] == slot[7] && slot[7] == slot[8]) {
                checkIfWon = true;
            }
            //first column
            if (slot[0] == slot[3] && slot[3] == slot[6]) {
                checkIfWon = true;
            }
            //second column
            if (slot[1] == slot[4] && slot[4] == slot[7]) {
                checkIfWon = true;
            }
            //third column
            if (slot[2] == slot[5] && slot[5] == slot[8]) {
                checkIfWon = true;
            }
            //top left to bottom right diagonal
            if (slot[0] == slot[4] && slot[4] == slot[8]) {
                checkIfWon = true;
            }
            //bottom left to top right diagonal
            if (slot[6] == slot[4] && slot[4] == slot[2]) {
                checkIfWon = true;
            }
            //money += winningMoney;
        }
        if (winningMoney > 0) {
            money += winningMoney;
            return "Winner!!";
        } else if (checkIfWon == true)
            return "Winner!!";
        else if (MainMenuView.gamemode == "Simulated Casino") {
            money -= bettingMoney;
            return "Bad Luck!!";
        } else
            return "Bad Luck!!";
    }

    /**
     * exit method to exit slot-machine and go back to menu.
     */
    public void exit() {
        MainMenuModel.money = money;
        menuModel.setVisible();
    }
}


