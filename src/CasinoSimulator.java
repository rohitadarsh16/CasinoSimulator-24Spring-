import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class CasinoSimulator {
    public static int money;

    public static void main(String[] args) {
        JFrame frame = new JFrame("CasinoSimulator");

        frame.setMinimumSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BufferedImage card = null;
        String path = System.getProperty("user.dir");

        try {
            card = ImageIO.read(new File(path + "/card-hearts-1.png"));
        }
        catch (Exception e) { System.out.println("Cannot load image!\n"); }

        JLabel label = new JLabel(new ImageIcon(card), SwingConstants.CENTER);

        frame.getContentPane().add(label);

        frame.pack();
        frame.setVisible(true);
    }
    //Skyler Shaw's Comment
    //Hayden Downs's Comment
    //Thien Le's Comment
}
