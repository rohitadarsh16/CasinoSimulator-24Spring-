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

    public SlotView(SlotModel slot) {
        super("CasinoSimulator - Slot Machine");
        slotModel = slot;

        setSize(700, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        BufferedImage[] picture;
        File path = new File("Assets/Slotmachine");
        File[] allFiles = path.listFiles();
        String contents[] = path.list();

        picture = new BufferedImage[allFiles.length];

        JLabel label[] = new JLabel[allFiles.length];
        //Put symbols to each icon variable.
        ImageIcon[] icon = new ImageIcon[contents.length];

        for(int i = 0; i < contents.length; i++) {
            //System.out.println(contents[i]);
            try{
                picture[i] = ImageIO.read(allFiles[i]);
                label[i] = new JLabel();
                icon[i]= new ImageIcon(picture[i]);
                //ImageIcon icon = new ImageIcon(picture[i]);
                //label[i].setIcon(icon);
                //add(label[i]);
                //setVisible(true);
            }catch (IOException e) {

            }
        }

        //There will be 7 icons for each label to use instead of 7 label for each symbol.
        label[0].setIcon(icon[0]);
        label[1].setIcon(icon[0]);
        label[2].setIcon(icon[0]);

        label[0].setBounds(150, 100, 100, 80);
        label[1].setBounds(300, 100, 100, 80);
        label[2].setBounds(450, 100, 100, 80);

        add(label[0]);
        add(label[1]);
        add(label[2]);


        JButton pullLever = new JButton("Pull");
        JLabel resultLabel = new JLabel();
        resultLabel.setBounds(300, 200, 100, 50);
        pullLever.setBounds(300, 300, 100, 100);
        add(pullLever);
        pullLever.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                slotModel.pullLever();
                label[0].setIcon(icon[slot.getSlot1()]);
                label[1].setIcon(icon[slot.getSlot2()]);
                label[2].setIcon(icon[slot.getSlot3()]);

                add(label[0]);
                add(label[1]);
                add(label[2]);

                resultLabel.setText(slot.matchCheck());
                resultLabel.setHorizontalAlignment(JLabel.CENTER);


            }
        });
        add(resultLabel);

        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(300, 500, 100, 100);
        add(exitBtn);

        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                slotModel.exit();
                dispose();
            }
        });

        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
