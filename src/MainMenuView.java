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
    private JLabel background;
    private JLabel blackjackMenu;
    private JLabel slotMenu;
    private JLabel blackjackText;
    private JLabel slotText;


    public MainMenuView(MainMenuModel menu) {
        super("CasinoSimulator");
        menuModel = menu;

        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JLabel jLabel = new JLabel();
        jLabel.setBounds(20, 0, 100, 60);
        jLabel.setText("Gamemode: ");
        jLabel.setForeground(Color.WHITE);
        String[] playOptions = {"Freeplay", "Simulated Casino"};
        JComboBox<String> jComboBox = new JComboBox<>(playOptions);
        jComboBox.setBounds(100, 0, 100, 60);
        add(jComboBox);
        add(jLabel);

        LoadAssets();

        //blackjack label
        blackjackText = new JLabel("Blackjack");
        blackjackText.setForeground(Color.WHITE);
        blackjackText.setBounds(365, 150, 80, 50);
        blackjackText.setFont(new Font("Dialog", Font.BOLD, 16));
        blackjackText.setVisible(false);

        //slot machine text
        slotText = new JLabel("Slot Machine");
        slotText.setForeground(Color.WHITE);
        slotText.setBounds(115, 150, 110, 50);
        slotText.setFont(new Font("Dialog", Font.BOLD, 16));
        slotText.setVisible(false);

        //code to click slot image
        slotMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                gamemode = jComboBox.getItemAt(jComboBox.getSelectedIndex());
                setVisible(false);
                menuModel.startSlot();
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

        //Cards when clicked now start blackjack game
        blackjackMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                gamemode = jComboBox.getItemAt(jComboBox.getSelectedIndex());
                setVisible(false);
                menuModel.startBlackjack();
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
        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(435, 450, 100, 100);
        add(exitBtn);

        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuModel.exit();
            }
        });

        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);

        add(background);
        background.add(blackjackText);
        background.add(slotText);
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
        blackjackMenu.setBounds(320, 200, 200, 200);
        background.add(blackjackMenu);

        //load Slotmachine Icon
        try {
            img = ImageIO.read(new File(path + "/Assets/MainMenu/SlotMachineMenu.png"));
        } catch (Exception e) {
            System.out.println("Cannot load SlotMachineMenu image!");
        }
        slotMenu = new JLabel(new ImageIcon(img));
        slotMenu.setBounds(50, 160, 250, 200);
        background.add(slotMenu);

    }

}
