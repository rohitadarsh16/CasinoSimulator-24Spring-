import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class MainMenuView extends JFrame {
    private MainMenuModel menuModel;
    public static String gamemode;

    private JComboBox<String> gamemodeSelect;
  
    public static String difficulty;
    private JLabel background;
    private JLabel blackjackMenu;
    private JLabel slotMenu;
    private JLabel blackjackText;
    private JLabel slotText;
    private JLabel saveButton;
    private JLabel saveText;
    private JLabel helpButton;
    private JLabel helpText;
    private JLabel difficultyText;
    private JLabel moneyTotal;
    private JLabel gameRules;

    public MainMenuView(MainMenuModel menu) {
        super("CasinoSimulator");
        menuModel = menu;


        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        //gamemode dropdown code
        JLabel gamemodeTxt = new JLabel();
        gamemodeTxt.setBounds(20, 0, 130, 60);
        gamemodeTxt.setText("Gamemode: ");
        gamemodeTxt.setForeground(Color.WHITE);
        gamemodeTxt.setFont(new Font("Dialog", Font.BOLD, 16));
        String[] playOptions = {"Simulated Casino", "Freeplay"};
        gamemodeSelect = new JComboBox<>(playOptions);
        gamemodeSelect.setBounds(120, 0, 140, 60);

        //difficulty dropdown code
        String[] difficultyOptions = {"Hard", "Medium", "Easy"};
        JComboBox<String> difficultySelect = new JComboBox<>(difficultyOptions);
        difficultySelect.setBounds(120, 60, 140, 60);

        LoadAssets();

        //blackjack label
        blackjackText = new JLabel("Blackjack");
        blackjackText.setForeground(Color.WHITE);
        blackjackText.setBounds(365, 160, 80, 50);
        blackjackText.setFont(new Font("Dialog", Font.BOLD, 16));
        blackjackText.setVisible(false);

        //slot machine text
        slotText = new JLabel("Slot Machine");
        slotText.setForeground(Color.WHITE);
        slotText.setBounds(115, 160, 110, 50);
        slotText.setFont(new Font("Dialog", Font.BOLD, 16));
        slotText.setVisible(false);

        //save text
        saveText = new JLabel("Save");
        saveText.setForeground(Color.WHITE);
        saveText.setBounds(500, 65, 80, 20);
        saveText.setFont(new Font("Dialog", Font.BOLD, 16));
        saveText.setVisible(false);

        //rules text
        helpText = new JLabel("Rules");
        helpText.setForeground(Color.WHITE);
        helpText.setBounds(505, 512, 80, 20);
        helpText.setFont(new Font("Dialog", Font.BOLD, 16));
        helpText.setVisible(false);

        //difficulty text
        difficultyText = new JLabel("Difficulty:");
        difficultyText.setForeground(Color.WHITE);
        difficultyText.setBounds(20, 60, 100, 60);
        difficultyText.setFont(new Font("Dialiog", Font.BOLD, 16));

        //total balance text
        //MainMenuModel.getMoney();
        moneyTotal = new JLabel("Total balance: $" + MainMenuModel.getMoney());
        moneyTotal.setForeground(Color.WHITE);
        moneyTotal.setBounds(10, 480, 300, 50);
        moneyTotal.setFont(new Font("Dialog", Font.BOLD, 16));


        //updates money value when player mouses over background
        background.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                moneyTotal.setText("Total balance: $" + MainMenuModel.getMoney());
            }
        });

        //save button code
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                menuModel.save();
            }
        });

        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                saveText.setVisible(true);
            }
            public void mouseExited(MouseEvent e){
                super.mouseExited(e);
                saveText.setVisible(false);
            }
        });

        //Help Button Code
        helpButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                helpText.setVisible(true);

            }
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                helpText.setVisible(false);
            }
        });

        helpButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                rulesBox();
            }
        });

        //slot machine code
        //code to click slot image
        slotMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                gamemode = gamemodeSelect.getItemAt(gamemodeSelect.getSelectedIndex());
                difficulty = difficultySelect.getItemAt(difficultySelect.getSelectedIndex());
                if(MainMenuModel.getMoney() <= 0 && gamemode == "Simulated Casino"){   //if player has no money and wants to play simulated casino, add 100 and notify
                    MainMenuModel.addMoney();
                    infoBox("$100 has been added", "You Ran Out of Money!");
                    setVisible(false);
                    menuModel.startSlot();
                }
                else{
                    gamemode = gamemodeSelect.getItemAt(gamemodeSelect.getSelectedIndex());
                    difficulty = difficultySelect.getItemAt(difficultySelect.getSelectedIndex());
                    setVisible(false);
                    menuModel.startSlot();
                }
            }
        });

        //code for rollover effect on slot image
        slotMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                slotText.setVisible(true);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                slotText.setVisible(false);
            }
        });

        //blackjack code
        //Cards when clicked now start blackjack game
        blackjackMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                gamemode = gamemodeSelect.getItemAt(gamemodeSelect.getSelectedIndex());
                difficulty = difficultySelect.getItemAt(difficultySelect.getSelectedIndex());
                if(MainMenuModel.getMoney() <= 0 && gamemode == "Simulated Casino") { //if player has no money and wants to play simulated casino, add 100 and notify
                    MainMenuModel.addMoney();
                    infoBox("$100 has been added", "You Ran Out of Money!");
                    setVisible(false);
                    menuModel.startBlackjack();
                }
                else {
                    gamemode = gamemodeSelect.getItemAt(gamemodeSelect.getSelectedIndex());
                    difficulty = difficultySelect.getItemAt(difficultySelect.getSelectedIndex());
                    setVisible(false);
                    menuModel.startBlackjack();
                }
            }
        });

        //code for rollover effect on blackjack card image
        blackjackMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                blackjackText.setVisible(true);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                blackjackText.setVisible(false);
            }
        });


        //exit button
        /*
        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(435, 450, 100, 100);
        add(exitBtn);

        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuModel.exit();
            }
        });
        */

        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);

        add(background);
        background.add(gamemodeSelect);
        background.add(gamemodeTxt);
        background.add(blackjackText);
        background.add(slotText);
        background.add(saveText);
        background.add(helpText);
        background.add(difficultyText);
        background.add(difficultySelect);
        background.add(moneyTotal);
    }

    /**
     * creates pop-up window for running out of money
     * @param infoMessage (what the message will say)
     * @param titleBar    (text in the title)
     */
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "Attention! " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * creates pop-up window with rules
     */
    public void rulesBox(){
        BufferedImage img = null;
        String path = System.getProperty("user.dir"); // get main folder path

        //create pop-up frame
        JFrame ruleFrame = new JFrame();
        JDialog jd = new JDialog(ruleFrame);
        jd.setLayout(new FlowLayout());
        jd.setBounds(375, 275, 550, 600);

        //load picture of rules
        try {
            img = ImageIO.read(new File(path + "/Assets/MainMenu/GameRules.png"));
        } catch (Exception e) {
            System.out.println("Cannot load BlackjackMenu image!");
        }
        gameRules = new JLabel(new ImageIcon(img));
        gameRules.setBounds(5, 5, 200, 200);

        //close button
        JButton close = new JButton("Close");
        close.setBounds(400, 400, 100, 100);
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jd.setVisible(false);
            }
        });
        jd.add(gameRules);
        jd.add(close);
        jd.setVisible(true);
    }

    /**
     * Loads all pictures from the assets folder
     */
    private void LoadAssets() {

        BufferedImage img = null;
        String path = System.getProperty("user.dir"); // get main folder path

        // Load background
        try {
            img = ImageIO.read(new File(path + "/Assets/MainMenu/MenuBackground1.jpg"));
        }
        catch (Exception e) { System.out.println("Cannot load background image!"); }
        background = new JLabel(new ImageIcon(img));
        background.setBounds(0, 0, 600, 600);

        //load Blackjack Icon
        try {
            img = ImageIO.read(new File(path + "/Assets/MainMenu/BlackjackMenu.png"));
        } catch (Exception e) {
            System.out.println("Cannot load BlackjackMenu image!");
        }
        blackjackMenu = new JLabel(new ImageIcon(img));
        blackjackMenu.setBounds(320, 210, 200, 200);
        background.add(blackjackMenu);

        //load Slotmachine Icon
        try {
            img = ImageIO.read(new File(path + "/Assets/MainMenu/SlotMachineMenu.png"));
        } catch (Exception e) {
            System.out.println("Cannot load SlotMachineMenu image!");
        }
        slotMenu = new JLabel(new ImageIcon(img));
        slotMenu.setBounds(50, 170, 250, 200);
        background.add(slotMenu);

        //load save button
        try {
            img = ImageIO.read(new File(path + "/Assets/MainMenu/SaveButtonResized2.png"));
        } catch (Exception e) {
            System.out.println("Cannot load SaveButton image!");
        }
        saveButton = new JLabel(new ImageIcon(img));
        saveButton.setBounds(500, 20, 40, 40);
        background.add(saveButton);

        //load help button
        try {
            img = ImageIO.read(new File(path + "/Assets/MainMenu/HelpButton2.png"));
        } catch (Exception e) {
            System.out.println("Cannot load HelpButton image!");
        }
        helpButton = new JLabel(new ImageIcon(img));
        helpButton.setBounds(495, 450, 60, 60);
        background.add(helpButton);

    }

    /**
     * For testing
     */
    public JLabel getBlackLabel() { return blackjackMenu; }
    public JComboBox<String> getGameOptions() { return gamemodeSelect; }
}
