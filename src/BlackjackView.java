import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlackjackView extends JFrame {
    private BlackjackModel blackjackModel;

    public BlackjackView(BlackjackModel black) {
        super("CasinoSimulator - Blackjack");
        blackjackModel = black;

        blackjackModel.createDeck();
        blackjackModel.shuffle();

        setSize(300, 400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        JLabel dealerLabel = new JLabel("Dealer");
        dealerLabel.setBounds(130, 10, 50, 30);

        JLabel playerLabel = new JLabel("Player");
        playerLabel.setBounds(130, 150, 50, 30);

        JButton dealBtn = new JButton("Deal");
        dealBtn.setBounds(20, 250, 70, 30);

        JButton standBtn = new JButton("Stand");
        standBtn.setBounds(95, 250, 70, 30);
        standBtn.setEnabled(false);

        JButton hitBtn = new JButton("Hit");
        hitBtn.setBounds(170, 250, 70, 30);
        hitBtn.setEnabled(false);

        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(100, 330, 100, 30);

        dealBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blackjackModel.deal();
                int[] cards;
                JLabel cardLabel;
                int x = 30; // x position for card labels

                for (int i = 0; i < 4; i++) {
                    if (i % 2 == 0) {
                        cards = blackjackModel.popPlayerCard();
                        cardLabel = new JLabel(cards[0] + " " + cards[1]);
                        cardLabel.setBounds(x, 190, 50, 30);
                        add(cardLabel);
                    }
                    else {
                        cards = blackjackModel.popDealerCard();
                        cardLabel = new JLabel(cards[0] + " " + cards[1]);
                        cardLabel.setBounds(x, 50, 50, 30);
                        add(cardLabel);
                    }
                    x += 30;
                }
                dealBtn.setEnabled(false);
                standBtn.setEnabled(true);
                hitBtn.setEnabled(true);
                repaint();
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
        add(playerLabel);
        add(dealBtn);
        add(hitBtn);
        add(standBtn);
        add(exitBtn);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
