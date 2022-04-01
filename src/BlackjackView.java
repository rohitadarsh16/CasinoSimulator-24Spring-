import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

public class BlackjackView extends JFrame {
    private BlackjackModel blackjackModel;
    private JLabel[][] deckLabel; // holds all pictures of cards
    private JLabel cardBack; // back of card picture
    private int[] hiddenCard; // the last card dealt to the dealer is turned face down

    private JLabel playerTotal;
    private JLabel dealerTotal;

    private int x_player; // x position for player cards
    private int x_dealer; // x position for dealer cards
    private final int Y_DEALER = 70; // y position for dealer cards
    private final int Y_PLAYER = 304; // y position for player cards

    public BlackjackView(BlackjackModel black) {
        super("CasinoSimulator - Blackjack");
        blackjackModel = black;

        x_dealer = x_player = 30;
        hiddenCard = new int[2];

        // init window
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        // Dealer wins
        JLabel dealerWin = new JLabel( "Dealer Wins!");
        dealerWin.setBounds(260, 500, 100, 70);
        dealerWin.setVisible(false);

        // Player wins
        JLabel playerWin = new JLabel("Player Wins!");
        playerWin.setBounds(260, 500, 100, 70);
        playerWin.setVisible(false);

        // It's a tie
        JLabel draw = new JLabel("It's a Draw");
        draw.setBounds(260, 450, 100, 70);
        draw.setVisible(false);

        // Labels
        JLabel dealerLabel = new JLabel("Dealer");
        dealerLabel.setBounds(275, 10, 50, 30);

        dealerTotal = new JLabel("0");
        dealerTotal.setBounds(330, 10, 50, 30);

        JLabel playerLabel = new JLabel("Player");
        playerLabel.setBounds(275, 234, 50, 30);

        playerTotal = new JLabel("0");
        playerTotal.setBounds(330, 234, 50, 30);

        // Game buttons
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

        // Deal button code
        dealBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blackjackModel.deal();
                dealBtn.setEnabled(false);
                standBtn.setEnabled(true);
                hitBtn.setEnabled(true);
                repaint();
            }
        });

        // Stand button code
        standBtn.addActionListener(new ActionListener() {   //player hit stand button
            @Override
            public void actionPerformed(ActionEvent e) {
                hitBtn.setEnabled(false);
                standBtn.setEnabled(false);
                blackjackModel.playerStand();

                if (blackjackModel.getCurrentState() == BlackjackModel.currentState.pWin) { //player wins by either having more points or dealer busts
                    playerWin.setVisible(true);
                } else if (blackjackModel.getCurrentState() == BlackjackModel.currentState.dWin) { //dealer wins by having more points
                    dealerWin.setVisible(true);
                } else if (blackjackModel.getCurrentState() == BlackjackModel.currentState.draw) { //both player and dealer have same point values
                    draw.setVisible(true);
                }

                repaint();
            }
        });

        // Exit button code
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blackjackModel.exit();
                dispose();
            }
        });

        // Hit button code
        hitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blackjackModel.playerHit();

                if (blackjackModel.getCurrentState() == BlackjackModel.currentState.dWin) { // player busted while hitting
                    dealerWin.setVisible(true);
                    hitBtn.setEnabled(false);
                    standBtn.setEnabled(false);
                } else if (blackjackModel.getCurrentState() == BlackjackModel.currentState.dTurn) {
                    blackjackModel.dealerTurn();
                } else if (blackjackModel.getCurrentState() == BlackjackModel.currentState.pWin) { //player got Blackjack while hitting
                    playerWin.setVisible(true);
                    hitBtn.setEnabled(false);
                    standBtn.setEnabled(false);
                }

                repaint();
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
        add(dealerWin);
        add(playerWin);
        add(draw);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void ShowPlayerCard(int[] c) {
        deckLabel[c[0]][c[1]].setBounds(x_player, Y_PLAYER, 96, 144);
        x_player += 96;
        add(deckLabel[c[0]][c[1]]);
        playerTotal.setText(String.valueOf(blackjackModel.getPlayerTotal()));
    }

    public void ShowDealerCard(int[] c) {
        deckLabel[c[0]][c[1]].setBounds(x_dealer, Y_DEALER, 96, 144);
        x_dealer += 96;
        add(deckLabel[c[0]][c[1]]);
        dealerTotal.setText(String.valueOf(blackjackModel.getDealerTotal()));
    }

    public void ShowBackCard(boolean visible) {
        if (visible) {
            cardBack.setBounds(x_dealer, Y_DEALER, 96, 144);
            add(cardBack);
        }
        else
            cardBack.setVisible(false);
    }

    /**
     * Load all pictures from Assets folder.
     */
    private void LoadAssets() {
        deckLabel = new JLabel[4][13]; // 4 rows & 13 columns; the rows are the suits
        BufferedImage img = null;
        String path = System.getProperty("user.dir"); // get main folder path
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

        // Load back of card picture.
        try {
            img = ImageIO.read(new File(path + "/Assets/Blackjack/card-back2.png"));
        }
        catch (Exception e) { System.out.println("Cannot load image!"); }
        cardBack = new JLabel(new ImageIcon(img));
    }
}
