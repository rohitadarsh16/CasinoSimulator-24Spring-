import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TempleAlertView extends JFrame{
    private TempleAlertModel templeAlertModel;

    private JLabel titleLabel;
    private JLabel postLabel;
    private JTextField postField;
    private JButton jButton;
    private JButton jButtonExit;

    public TempleAlertView(TempleAlertModel model) {
        super("Guess the Number Game");
        this.templeAlertModel = model;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 100);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        titleLabel = new JLabel("Latest post from www.reddit.com/r/Temple:");
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align to the left
        add(titleLabel);

        postLabel = new JLabel("post content");
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align to the left
        add(postLabel);

        postField = new JTextField(10);
        add(postField);

        //feedbackLabel.setText("Please enter a valid number.");

        add(jButton);
        jButtonExit = new JButton("Exit");
        jButtonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                templeAlertModel.exit();
            }
        });
        add(jButtonExit);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void updateGameView() {
        titleLabel.setText("Latest post from www.reddit.com/r/Temple:");

        templeAlertModel.currentState gameState = templeAlertModel.getGameState();
        jButton.setText("Bet");
        postLabel.setText("Current Bet:");
    }

    public void showGameOverMessage() {
        JOptionPane.showMessageDialog(this, "Game over! You've run out of money.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

}
