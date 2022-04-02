import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

public class BlackjackView extends JFrame {
    private BlackjackModel blackjackModel;
    private JLabel[][] deckLabel; // holds all pictures of cards
    private LinkedList<JLabel> cards; // holds the currently dealt cards
    private JLabel cardBack; // back of card picture
    private JLabel background;

    private JLabel balanceTotal;
    private JLabel betTotal;
    private JLabel playerTotal;
    private JLabel dealerTotal;
    private JLabel dealerWin;
    private JLabel playerWin;
    private JLabel draw;

    private JButton dealBtn;
    private JButton standBtn;
    private JButton hitBtn;
    private JButton exitBtn;

    private int x_player; // x position for player cards
    private int x_dealer; // x position for dealer cards
    private final int Y_DEALER = 70; // y position for dealer cards
    private final int Y_PLAYER = 304; // y position for player cards

    public BlackjackView(BlackjackModel black) {
        super("CasinoSimulator - Blackjack");
        blackjackModel = black;
        x_dealer = x_player = 30;
        cards = new LinkedList<>();

        // init window
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        // Balance and bet labels
        JLabel balanceLabel = new JLabel("Balance: $");
        balanceLabel.setBounds(470, 10, 63, 30);
        balanceLabel.setForeground(Color.WHITE);

        balanceTotal = new JLabel("0");
        balanceTotal.setBounds(533, 10, 50, 30);
        balanceTotal.setForeground(Color.WHITE);

        JLabel betLabel = new JLabel("Bet: $");
        betLabel.setBounds(470, 50, 35, 30);
        betLabel.setForeground(Color.WHITE);

        betTotal = new JLabel("0");
        betTotal.setBounds(505, 50, 50, 30);
        betTotal.setForeground(Color.WHITE);

        // Dealer wins label
        dealerWin = new JLabel( "Dealer Wins!");
        dealerWin.setBounds(260, 500, 100, 70);
        dealerWin.setForeground(Color.WHITE);
        dealerWin.setVisible(false);

        // Player wins label
        playerWin = new JLabel("Player Wins!");
        playerWin.setBounds(260, 500, 100, 70);
        playerWin.setForeground(Color.WHITE);
        playerWin.setVisible(false);

        // It's a tie label
        draw = new JLabel("It's a Draw");
        draw.setBounds(260, 450, 100, 70);
        draw.setForeground(Color.WHITE);
        draw.setVisible(false);

        // Other labels
        JLabel dealerLabel = new JLabel("Dealer");
        dealerLabel.setBounds(275, 10, 50, 30);
        dealerLabel.setForeground(Color.WHITE);

        dealerTotal = new JLabel("0");
        dealerTotal.setBounds(330, 10, 50, 30);
        dealerTotal.setForeground(Color.WHITE);

        JLabel playerLabel = new JLabel("Player");
        playerLabel.setBounds(275, 234, 50, 30);
        playerLabel.setForeground(Color.WHITE);

        playerTotal = new JLabel("0");
        playerTotal.setBounds(330, 234, 50, 30);
        playerTotal.setForeground(Color.WHITE);

        // Game buttons
        dealBtn = new JButton("Deal");
        dealBtn.setBounds(145, 630, 100, 30);

        standBtn = new JButton("Stand");
        standBtn.setBounds(250, 630, 100, 30);
        standBtn.setEnabled(false);

        hitBtn = new JButton("Hit");
        hitBtn.setBounds(355, 630, 100, 30);
        hitBtn.setEnabled(false);

        exitBtn = new JButton("Exit");
        exitBtn.setBounds(250, 730, 100, 30);

        LoadAssets(); // load card images

        // Deal button code
        dealBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dealBtn.setEnabled(false);
                standBtn.setEnabled(true);
                hitBtn.setEnabled(true);
                blackjackModel.deal();
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
                repaint();
            }
        });

        add(background);
        background.add(cardBack);
        background.add(balanceLabel);
        background.add(betLabel);
        background.add(balanceTotal);
        background.add(betTotal);
        background.add(dealerLabel);
        background.add(dealerTotal);
        background.add(playerLabel);
        background.add(playerTotal);
        background.add(dealBtn);
        background.add(hitBtn);
        background.add(standBtn);
        background.add(exitBtn);
        background.add(dealerWin);
        background.add(playerWin);
        background.add(draw);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Show the player's card after it was dealt.
     */
    public void ShowPlayerCard(int[] c) {
        deckLabel[c[0]][c[1]].setBounds(x_player, Y_PLAYER, 96, 144);
        x_player += 96;
        background.add(deckLabel[c[0]][c[1]]);
        playerTotal.setText(String.valueOf(blackjackModel.getPlayerTotal()));
        cards.add(deckLabel[c[0]][c[1]]);
    }

    /**
     * Show the dealer's card after it was dealt.
     */
    public void ShowDealerCard(int[] c) {
        deckLabel[c[0]][c[1]].setBounds(x_dealer, Y_DEALER, 96, 144);
        x_dealer += 96;
        background.add(deckLabel[c[0]][c[1]]);
        dealerTotal.setText(String.valueOf(blackjackModel.getDealerTotal()));
        cards.add(deckLabel[c[0]][c[1]]);
    }

    /**
     * Show the back of a card.
     */
    public void ShowBackCard(boolean visible) { cardBack.setVisible(visible); }

    public void Reset(JLabel l) {
        ActionListener a = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                l.setVisible(false);
                dealerTotal.setText(String.valueOf(blackjackModel.getDealerTotal()));
                playerTotal.setText(String.valueOf(blackjackModel.getPlayerTotal()));

                for (JLabel j : cards)
                    background.remove(j);
                cards.clear();

                dealBtn.setEnabled(true);
                repaint();
            }
        };

        standBtn.setEnabled(false);
        hitBtn.setEnabled(false);
        x_dealer = x_player = 30;

        Timer t = new Timer(2000, a);
        t.setRepeats(false);
        t.start();
    }

    public void ShowDealerWin() { dealerWin.setVisible(true); Reset(dealerWin); }
    public void ShowPlayerWin() { playerWin.setVisible(true); Reset(playerWin); }
    public void ShowDraw() { draw.setVisible(true); Reset(draw); }

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
        catch (Exception e) { System.out.println("Cannot load card back image!"); }
        cardBack = new JLabel(new ImageIcon(img));
        cardBack.setBounds(126, Y_DEALER, 96, 144);
        cardBack.setVisible(false);

        // Load background
        try {
            img = ImageIO.read(new File(path + "/Assets/Blackjack/background.jpg"));
        }
        catch (Exception e) { System.out.println("Cannot load background image!"); }
        background = new JLabel(new ImageIcon(img));
        background.setBounds(0, 0, 600, 800);
    }
}
