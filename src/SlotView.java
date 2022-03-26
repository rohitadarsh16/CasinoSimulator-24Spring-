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

        setSize(700, 800);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        BufferedImage[] picture;
        File path = new File("Pictures");
        File[] allFiles = path.listFiles();
        String contents[] = path.list();

        picture = new BufferedImage[allFiles.length];

        JLabel label[] = new JLabel[allFiles.length];

        for(int i = 0; i < contents.length; i++) {
            //System.out.println(contents[i]);
            try{
                picture[i] = ImageIO.read(allFiles[i]);
                label[i] = new JLabel();
                ImageIcon icon = new ImageIcon(picture[i]);
                label[i].setIcon(icon);
                //add(label[i]);
                //setVisible(true);
            }catch (IOException e) {

            }
        }
        label[1].setBounds(150, 200, 100, 80);
        add(label[1]);
        label[2].setBounds(300, 200, 100, 80);
        add(label[2]);
        label[3].setBounds(450, 200, 100, 80);
        add(label[3]);

        JButton pullLever = new JButton("Pull");
        pullLever.setBounds(300, 500, 100, 100);
        add(pullLever);
        pullLever.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(300, 650, 100, 100);
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
