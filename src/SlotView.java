import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SlotView extends JFrame {
    private SlotModel slotModel;

    public SlotView(SlotModel slot) {
        super("CasinoSimulator - Slot Machine");
        slotModel = slot;

        setSize(800, 800);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        BufferedImage[] picture;
        File path = new File("Assets/SlotMachine");
        File[] allFiles = path.listFiles();
        String contents[] = path.list();

        //picture = new BufferedImage[allFiles.length];

        //create 9 labels for 9 slots
        JLabel label[] = new JLabel[9];
        for(int i = 0; i < 9; i++) {
            label[i] = new JLabel();
        }

        //Put symbols to each icon variable.
        ImageIcon[] icon = new ImageIcon[contents.length];
        try {
            icon[0] = new ImageIcon(ImageIO.read(new File("Assets/SlotMachine/apple.png")));
            icon[1] = new ImageIcon(ImageIO.read(new File("Assets/SlotMachine/slot7.png")));
            icon[2] = new ImageIcon(ImageIO.read(new File("Assets/SlotMachine/banana.png")));
            icon[3] = new ImageIcon(ImageIO.read(new File("Assets/SlotMachine/bell.png")));
            icon[4] = new ImageIcon(ImageIO.read(new File("Assets/SlotMachine/cherry.png")));
            icon[5] = new ImageIcon(ImageIO.read(new File("Assets/SlotMachine/grape.png")));
            icon[6] = new ImageIcon(ImageIO.read(new File("Assets/SlotMachine/orange.png")));
            icon[7] = new ImageIcon(ImageIO.read(new File("Assets/SlotMachine/pear.png")));
            icon[8] = new ImageIcon(ImageIO.read(new File("Assets/SlotMachine/triplebar.png")));
            icon[9] = new ImageIcon(ImageIO.read(new File("Assets/SlotMachine/watermelon.png")));
            icon[10] = new ImageIcon(ImageIO.read(new File("Assets/SlotMachine/diamond.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
        for(int i = 0; i < contents.length; i++) {
            try{
                picture[i] = ImageIO.read(allFiles[i]);
                icon[i]= new ImageIcon(picture[i]);
            }catch (IOException e) {

            }
        }
         */


        //Add a button for user to know which symbols are special or normal.
        JButton helpButton = new JButton(new ImageIcon("Assets/SlotMachineGUI/" + "HelpButtonSmaller.png"));
        helpButton.setBounds(355, 685, 35, 35);
        //helpButton.setBorder(BorderFactory.createEmptyBorder());
        //helpButton.setContentAreaFilled(false);
        add(helpButton);
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Copy from menu
                BufferedImage img = null;
                //String mainPath = System.getProperty("user.dir"); // get main folder path

                //create pop-up frame
                JFrame ruleFrame = new JFrame();
                JDialog symbolsDialog = new JDialog(ruleFrame);
                symbolsDialog.setLayout(new FlowLayout());
                symbolsDialog.setBounds(300, 100, 530, 450);

                //load picture of rules
                try {
                    img = ImageIO.read(new File("Assets/SlotMachineGUI/WinningSymbols.PNG"));
                } catch (Exception ex) {
                    System.out.println("Cannot load WinningSymbols image!");
                }
                JLabel symbolsRules = new JLabel(new ImageIcon(img));
                symbolsRules.setBounds(5, 5, 200, 200);

                //close button
                JButton close = new JButton("Close");
                close.setBounds(400, 400, 100, 100);
                close.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        symbolsDialog.setVisible(false);
                    }
                });
                symbolsDialog.add(symbolsRules);
                symbolsDialog.add(close);
                symbolsDialog.setVisible(true);
            }
        });





        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(700, 162, 75, 70);
        exitBtn.setBackground(Color.lightGray);
        add(exitBtn);

        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                slotModel.exit();
                dispose();
            }
        });

        JLabel slotlabel = new JLabel();
        slotlabel.setBounds(-70, -20, 900, 800);
        ImageIcon slotmachine = new ImageIcon("Assets/SlotMachineGUI/" + "slotframe.png");
        slotlabel.setIcon(slotmachine);

        label[0].setBounds(210, 300, 100, 80);
        label[1].setBounds(210, 380, 100, 80);
        label[2].setBounds(210, 460, 100, 80);
        label[3].setBounds(355, 300, 100, 80);
        label[4].setBounds(355, 380, 100, 80);
        label[5].setBounds(355, 460, 100, 80);
        label[6].setBounds(500, 300, 100, 80);
        label[7].setBounds(500, 380, 100, 80);
        label[8].setBounds(500, 460, 100, 80);

        //There will be 11 icons for each label
        for(int i = 0; i < 9; i++) {
            label[i].setIcon(icon[1]);

            add(label[i]);
        }

        JButton pullLever = new JButton(new ImageIcon("Assets/SlotMachineGUI/" + "handle.png"));
        JLabel resultLabel = new JLabel();

        resultLabel.setFont(new Font("Arial", Font.PLAIN, 36));
        resultLabel.setBounds(250, 580, 300, 50);   //result label size

        pullLever.setBounds(675, 350, 120, 320);  //Pull button size

        JLabel moneylabel = new JLabel();
        moneylabel.setFont(new Font("Arial", Font.BOLD, 20));
        moneylabel.setBounds(310, 730, 300, 50);

        JLabel wagerlabel = new JLabel();

        //add JComboBox for betting options.
        String[] betOptions = {"1$", "2$", "5$"};
        JComboBox<String> betSelect = new JComboBox<>(betOptions);
        betSelect.setBounds(260, 675, 60, 50);
        betSelect.setBackground(Color.CYAN);
        betSelect.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    if (e.getItem() == betOptions[0]) {
                        slot.setBettingMoney(1);
                    }
                    if (e.getItem() == betOptions[1]) {
                        slot.setBettingMoney(2);
                    }
                    if (e.getItem() == betOptions[2]) {
                        slot.setBettingMoney(5);
                    }
                }
            }
        });

        wagerlabel.setFont(new Font("Arial", Font.BOLD, 20));
        wagerlabel.setBounds(200, 675, 300, 50);
        wagerlabel.setText(("Bet = "));

        JLabel bonuslabel = new JLabel();
        bonuslabel.setFont(new Font("Arial", Font.BOLD, 15));
        bonuslabel.setBounds(400, 675, 300, 50);
        bonuslabel.setText(("<html>Normal Symbols = Bet x 2<br>Special Symbols = Bet x 5</html>"));

        pullLever.setBorder(BorderFactory.createEmptyBorder());
        pullLever.setContentAreaFilled(false);

        add(pullLever);
        moneylabel.setText(("TOTAL:" + "$" + slot.getMoney()));

        pullLever.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(slot.getMoney() == 0) {
                    //resultLabel.setFont(new Font("Arial", Font.PLAIN, 20));
                    //resultLabel.setText("<html><div style='text-align: center;'>No More money!<br> Add more money in the menu.</div></html>");
                    slotModel.exit();
                    dispose();

                }
                else if(slot.getMoney() < slot.getBettingMoney()) {
                    resultLabel.setFont(new Font("Arial", Font.PLAIN, 20));
                    resultLabel.setText("<html><div style='text-align: center;'>Not enough money for betting!<br> Please lower the bet.</div></html>");
                }
                else {

                    slotModel.pullLever();
                    for (int i = 0; i < 9; i++) {
                        label[i].setIcon(icon[slot.getSlot(i)]);

                        add(label[i]);
                    }
                    resultLabel.setText(slot.matchCheck());
                    resultLabel.setHorizontalAlignment(JLabel.CENTER);
                    moneylabel.setText(("TOTAL:" + "$" + slot.getMoney()));
                }
            }
        });
        add(betSelect);

        add(wagerlabel);
        add(bonuslabel);
        add(moneylabel);
        add(resultLabel);
        add(slotlabel);

        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
