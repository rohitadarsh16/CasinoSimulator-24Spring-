import java.util.Random;

public class SlotModel {
    private MainMenuModel menuModel;
    private SlotView slotView;
    private int money;

    private int[] slot = new int[9];
    private int bettingMoney = 1;

    public SlotModel(MainMenuModel menu, int money) {
        menuModel = menu;
        this.money = money;
        slotView = new SlotView(this);
    }

    public int getMoney() {
        return money;
    }

    public void pullLever() {

        Random randomNumber = new Random();
        if(MainMenuView.difficulty == "Hard") {
            for (int i = 0; i < 9; i++) {
                slot[i] = randomNumber.nextInt(7);
            }
        }
        else if(MainMenuView.difficulty == "Medium") {
            for (int i = 0; i < 9; i++) {
                int val = randomNumber.nextInt(8);
                if(val == 7) //Double the chance to get a "7" icon
                    val = 0;
                slot[i] = val;
            }
        }
        else {//Difficuly is set to easy...
            for (int i = 0; i < 9; i++) {
                int val = randomNumber.nextInt(9);
                if (val >= 7) //Triple the chance to get a "7" icon
                    val = 0;
                slot[i] = val;
            }
        }
    }

    //Get slot number methods. There should be a better way to do it because if we need 9 slots, then there are 9 get methods.
    public int getSlot(int a) {
        return slot[a];
    }

    public void setBettingMoney(int a) {
        bettingMoney = a;
    }

    //simple rules for 3by3 slots
    public String matchCheck2() {
        int winningMoney = 0;
        boolean checkIfWon = false;
        if (MainMenuView.gamemode == "Simulated Casino") {
            //first row
            if (slot[0] == slot[1] && slot[1] == slot[2]) {
                if (slot[0] == 0) {
                    winningMoney = bettingMoney * 5;
                } else {
                    winningMoney = bettingMoney * 2;
                }
            }
            //second row
            if (slot[3] == slot[4] && slot[4] == slot[5]) {
                if (slot[3] == 0) {
                    winningMoney = bettingMoney * 5;
                } else {
                    winningMoney = bettingMoney * 2;
                }
            }
            //third row
            if (slot[6] == slot[7] && slot[7] == slot[8]) {
                if (slot[6] == 0) {
                    winningMoney = bettingMoney * 5;
                } else {
                    winningMoney = bettingMoney * 2;
                }
            }
            //first column
            if (slot[0] == slot[3] && slot[3] == slot[6]) {
                if (slot[0] == 0) {
                    winningMoney = bettingMoney * 5;
                } else {
                    winningMoney = bettingMoney * 2;
                }
            }
            //second column
            if (slot[1] == slot[4] && slot[4] == slot[7]) {
                if (slot[1] == 0) {
                    winningMoney = bettingMoney * 5;
                } else {
                    winningMoney = bettingMoney * 2;
                }
            }
            //third column
            if (slot[2] == slot[5] && slot[5] == slot[8]) {
                if (slot[2] == 0) {
                    winningMoney = bettingMoney * 5;
                } else {
                    winningMoney = bettingMoney * 2;
                }
            }
            //top left to bottom right diagonal
            if (slot[0] == slot[4] && slot[4] == slot[8]) {
                if (slot[0] == 0) {
                    winningMoney = bettingMoney * 5;
                } else {
                    winningMoney = bettingMoney * 2;
                }
            }
            //bottom left to top right diagonal
            if (slot[6] == slot[4] && slot[4] == slot[2]) {
                if (slot[6] == 0) {
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
//            money += winningMoney;
        }
        if (winningMoney > 0) {
            money += winningMoney;
            return "Winner!!";
        }
        else if(checkIfWon == true)
            return "Winner!!";
        else if(MainMenuView.gamemode == "Simulated Casino") {
            money -= bettingMoney;
            return "Bad Luck!!";
        }
        else
            return "Bad Luck!!";
    }

    /*
    //simple rules for 1by3 slots
    public String matchCheck1() {
        if (MainMenuView.gamemode == "Simulated Casino") {
            //Some simple winning rules, modify if needed
            if (slot[1] == slot[2] && slot[2] == slot[3]) {
                //if 7 symbol is 3 in the row, then jackpot winner
                if (slot[1] == 0) {
                    money += 100;
                    return "Jackpot Winner!!!";
                    //Other 3 in the row
                } else {
                    money += 50;
                    return "Winner!!";
                }
                //2 in the row
            } else if (slot[1] == slot[2] || slot[2] == slot[3] || slot[3] == slot[1]) {
                return "Free Spin!";
                //No matches
            } else {
                return "Bad luck!";
            }
        }
        //If the gamemode is in freeplay
        else {
            if (slot[1] == slot[2] && slot[2] == slot[3]) {
                //if 7 symbol is 3 in the row, then jackpot winner
                if (slot[1] == 0) {
                    return "Jackpot Winner!!!";
                    //Other 3 in the row
                } else {
                    return "Winner!!";
                }
                //2 in the row
            } else if (slot[1] == slot[2] || slot[2] == slot[3] || slot[3] == slot[1]) {
                return "Free Spin!";
                //No matches
            } else {
                return "Bad luck!";
            }
        }
    }

     */

    public void exit() {
        MainMenuModel.money = money;
        menuModel.setVisible();
    }
}


