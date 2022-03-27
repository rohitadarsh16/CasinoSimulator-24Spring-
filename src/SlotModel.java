import java.util.Random;

public class SlotModel {
    private MainMenuModel menuModel;
    private SlotView slotView;
    private int money;

    private int slot1;
    private int slot2;
    private int slot3;

    public SlotModel(MainMenuModel menu, int money) {
        menuModel = menu;
        this.money = money;
        slotView = new SlotView(this);
    }

    public void pullLever() {
        //random number from 0-6, represent for 7 symbols: apple, grape, orange, cherry, “7”, diamond, heart
        Random ramdomNumber = new Random();
        slot1 = ramdomNumber.nextInt(7);
        slot2 = ramdomNumber.nextInt(7);
        slot3 = ramdomNumber.nextInt(7);


    }
    //Get slot number methods. There should be a better way to do it because if we need 9 slots, then there are 9 get methods.
    public int getSlot1 () {
        return slot1;
    }
    public int getSlot2 () {
        return slot2;
    }
    public int getSlot3 () {
        return slot3;
    }

    public String matchCheck() {

        //Some simple winning rules, modify if needed
        if (slot1 == slot2 && slot2 == slot3) {
            //if 7 symbol is 3 in the row, then jackpot winner
            if (slot1 == 0) {
                money += 100;
                return "Jackpot Winner!!!";
            //Other 3 in the row
            } else {
                money += 50;
                return "Winner!!";
            }
        //2 in the row
        } else if (slot1 == slot2 || slot2 == slot3 || slot3 == slot1) {
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


