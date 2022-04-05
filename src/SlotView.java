import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SlotView extends JFrame {
    private SlotModel slotModel;
    private boolean is3by3Mode = true;

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

        picture = new BufferedImage[allFiles.length];

        //create 9 labels for 9 slots
        JLabel label[] = new JLabel[9];
        for(int i = 0; i < 9; i++) {
            label[i] = new JLabel();
        }

        //Put symbols to each icon variable.
        ImageIcon[] icon = new ImageIcon[contents.length];

        for(int i = 0; i < contents.length; i++) {
            try{
                picture[i] = ImageIO.read(allFiles[i]);
                icon[i]= new ImageIcon(picture[i]);
            }catch (IOException e) {

            }
        }

        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(690, 162, 88, 82);
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

        //There will be 7 icons for each label
        for(int i = 0; i < 9; i++) {
            label[i].setIcon(icon[0]);

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
        wagerlabel.setFont(new Font("Arial", Font.BOLD, 20));
        wagerlabel.setBounds(240, 675, 300, 50);
        wagerlabel.setText(("Bet = $1"));

        JLabel bonuslabel = new JLabel();
        bonuslabel.setFont(new Font("Arial", Font.BOLD, 20));
        bonuslabel.setBounds(400, 675, 300, 50);
        bonuslabel.setText(("Bonus = Bet x 5"));

        pullLever.setBorder(BorderFactory.createEmptyBorder());
        pullLever.setContentAreaFilled(false);

        add(pullLever);
        moneylabel.setText(("TOTAL:" + "$" + slot.getMoney()));

        pullLever.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                slotModel.pullLever();
                for (int i = 0; i < 9; i++) {
                    label[i].setIcon(icon[slot.getSlot(i)]);

                    add(label[i]);
                }
                resultLabel.setText(slot.matchCheck2());
                resultLabel.setHorizontalAlignment(JLabel.CENTER);
                moneylabel.setText(("TOTAL:" + "$" + slot.getMoney()));
            }
        });
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
