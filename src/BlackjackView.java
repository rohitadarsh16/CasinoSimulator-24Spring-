import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class BlackjackView extends JFrame {
    private BlackjackModel blackjackModel;
    private JLabel[][] deckLabel;
    private JLabel cardBack;
    private int[] hiddenCard;

    private int x; // x position for cards

    public BlackjackView(BlackjackModel black) {
        super("CasinoSimulator - Blackjack");
        blackjackModel = black;

        x = 30;
        hiddenCard = new int[2];

        blackjackModel.createDeck();
        blackjackModel.shuffle();

        setSize(600, 800);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        JLabel dealerLabel = new JLabel("Dealer");
        dealerLabel.setBounds(275, 10, 50, 30);

        JLabel dealerTotal = new JLabel("0");
        dealerTotal.setBounds(330, 10, 50, 30);

        JLabel playerLabel = new JLabel("Player");
        playerLabel.setBounds(275, 234, 50, 30);

        JLabel playerTotal = new JLabel("0");
        playerTotal.setBounds(330, 234, 50, 30);

        JButton dealBtn = new JButton("Deal");
        dealBtn.setBounds(145, 630, 100, 30);

        JButton standBtn = new JButton("Stand");
        standBtn.setBounds(250, 630, 100, 30);
        standBtn.setEnabled(false);

        JButton hitBtn = new JButton("Hit");
        hitBtn.setBounds(355, 630, 100, 30);
        hitBtn.setEnabled(false);

        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(250, 730, 100, 30);

        LoadAssets(); // load card images

        dealBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blackjackModel.deal();
                int[] c = null;
                int y; // y position for card labels

                for (int i = 0; i < 4; i++) {
                    if (i % 2 == 0) {
                        y = 304;
                        c = blackjackModel.popPlayerCard();
                        deckLabel[c[0]][c[1]].setBounds(x, y, 96, 144);
                    }
                    else {
                        y = 70;
                        c = blackjackModel.popDealerCard();
                        deckLabel[c[0]][c[1]].setBounds(x, y, 96, 144);
                        x += 96;
                    }
                    add(deckLabel[c[0]][c[1]]);
                    if (i == 3) {
                        deckLabel[c[0]][c[1]].setVisible(false);
                        hiddenCard = c.clone();
                    }
                }

                cardBack.setBounds(126, 70, 96, 144);
                add(cardBack);

                dealerTotal.setText(String.valueOf(blackjackModel.getDealerTotal() - c[2]));
                playerTotal.setText(String.valueOf(blackjackModel.getPlayerTotal()));

                dealBtn.setEnabled(false);
                standBtn.setEnabled(true);
                hitBtn.setEnabled(true);
                repaint();
            }
        });

        standBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deckLabel[hiddenCard[0]][hiddenCard[1]].setVisible(true);
                cardBack.setVisible(false);
                dealerTotal.setText(String.valueOf(blackjackModel.getDealerTotal()));
            }
        });

        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blackjackModel.exit();
                dispose();
            }
        });

        add(dealerLabel);
        add(dealerTotal);
        add(playerLabel);
        add(playerTotal);
        add(dealBtn);
        add(hitBtn);
        add(standBtn);
        add(exitBtn);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void LoadAssets() {
        deckLabel = new JLabel[4][13];
        BufferedImage img = null;
        String path = System.getProperty("user.dir");
        String[] suit = new String[] { "clubs", "diamonds", "hearts", "spades" };

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                try {
                    img = ImageIO.read(new File(path + "/Assets/Blackjack/" + "card-" + suit[i] + "-" + (j+1) + ".png"));
                }
                catch (Exception e) { System.out.println("Cannot load image!"); }

                deckLabel[i][j] = new JLabel(new ImageIcon(img));
            }
        }

        try {
            img = ImageIO.read(new File(path + "/Assets/Blackjack/card-back2.png"));
        }
        catch (Exception e) { System.out.println("Cannot load image!"); }
        cardBack = new JLabel(new ImageIcon(img));
    }
}
