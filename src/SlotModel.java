import java.util.Random;

public class SlotModel {
    private MainMenuModel menuModel;
    private SlotView slotView;
    private int money;

    private int[] slot = new int[9];

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
    public int getSlot (int a) {
        return slot[a];
    }

    //simple rules for 3by3 slots
    public String matchCheck2() {
        int result= 0;
        //first row
        if (slot[0] == slot[1] && slot[1] == slot[2]) {
            result ++;
        }
        //second row
        if (slot[3] == slot[4] && slot[4] == slot[5]) {
            result ++;
        }
        //third row
        if (slot[6] == slot[7] && slot[7] == slot[8]) {
            result ++;
        }
        //first column
        if (slot[0] == slot[3] && slot[3] == slot[6]) {
            result ++;
        }
        //second column
        if (slot[1] == slot[4] && slot[4] == slot[7]) {
            result ++;
        }
        //third column
        if (slot[2] == slot[5] && slot[5] == slot[8]) {
            result ++;
        }
        //top left to bottom right diagonal
        if (slot[0] == slot[4] && slot[4] == slot[8]) {
            result ++;
        }
        //bottom left to top right diagonal
        if (slot[6] == slot[4] && slot[4] == slot[2]) {
            result ++;
        }
        if (result > 0) {
            if(MainMenuView.gamemode == "Simulated Casino")
                money += 5;
            return "Winner!!";
        }
        else {
            if(MainMenuView.gamemode == "Simulated Casino")
                money--;
            return "Bad Luck!!";

        }
    }

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


    public void exit() {
        MainMenuModel.money = money;
        menuModel.setVisible();
    }
}


