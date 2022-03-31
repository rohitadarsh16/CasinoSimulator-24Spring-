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

    public void pullLever() {
        Random ramdomNumber = new Random();
        for (int i = 0; i < 9; i++) {
            slot[i] = ramdomNumber.nextInt(7);
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
            return "Winner!!";
        }
        else {
            return "Bad Luck!!";
        }
    }

    //simple rules for 1by3 slots
    public String matchCheck1() {

        //Some simple winning rules, modify if needed
        if (slot[0] == slot[1] && slot[1] == slot[2]) {
            //if 7 symbol is 3 in the row, then jackpot winner
            if (slot[0] == 0) {
                money += 100;
                return "Jackpot Winner!!!";
            //Other 3 in the row
            } else {
                money += 50;
                return "Winner!!";
            }
        //2 in the row
        } else if (slot[0] == slot[1] || slot[1] == slot[2] || slot[2] == slot[0]) {
            return "Free Spin!";
        //No matches
        } else {
            return "Bad luck!";
        }

    }



    public void exit() {
        menuModel.setVisible();
    }
}


