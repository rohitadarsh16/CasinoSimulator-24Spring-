import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;

public class MainMenuModel {
    private MainMenuView menuView;
    private SlotModel slotModel;
    private BlackjackModel blackjackModel;

    public static int money; // available money to play

    public MainMenuModel() {
        try {
            File myObj = new File("savedata.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                money = myReader.nextInt();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("No save data.");
            money = 100;
        }
        menuView = new MainMenuView(this);
    }

    /**
     * Make MainMenu window visible.
     */
    public void setVisible() {
        menuView.setVisible(true);
    }

    public void startSlot() {
        slotModel = new SlotModel(this, money);
    }

    public void startBlackjack() { blackjackModel = new BlackjackModel(this, money); }

    public void exit() {
        //Create a new file called savedata.txt if it does not already exist
        try {
            File savedata = new File("savedata.txt");
            if (savedata.createNewFile()) {
                System.out.println("File created: " + savedata.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException ex) {
            System.out.println("An error occurred.");
            ex.printStackTrace();
        }
        //Write the current value of money to savedata.txt, and overwrite any data that is currently there
        try {
            FileWriter writer = new FileWriter("savedata.txt", false);
            writer.write(String.valueOf(money));
            writer.close();
        } catch (IOException exx) {
            exx.printStackTrace();
        }
    System.exit(0);
    }

    /**
     * For testing
     */
    public MainMenuView getView() { return menuView; }
    public BlackjackModel getModel() { return blackjackModel; }
}
