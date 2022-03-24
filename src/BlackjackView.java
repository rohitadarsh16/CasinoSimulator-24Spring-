import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlackjackView extends JFrame {
    private BlackjackModel blackjackModel;

    public BlackjackView(BlackjackModel black) {
        super("CasinoSimulator - Blackjack");
        blackjackModel = black;

        setSize(300, 400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(100, 100, 100, 100);
        add(exitBtn);

        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blackjackModel.exit();
                dispose();
            }
        });

        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
