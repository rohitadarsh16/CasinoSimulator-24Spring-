import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuView extends JFrame {
    private MainMenuModel menuModel;

    public MainMenuView(MainMenuModel menu) {
        super("CasinoSimulator");
        menuModel = menu;

        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JButton slotBtn = new JButton("Slot Machine");
        slotBtn.setBounds(100, 50, 100, 100);
        add(slotBtn);

        slotBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                menuModel.startSlot();
            }
        });

        JButton blackBtn = new JButton("Blackjack");
        blackBtn.setBounds(100, 150, 100, 100);
        add(blackBtn);

        blackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                menuModel.startBlackjack();
            }
        });

        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(100, 250, 100, 100);
        add(exitBtn);

        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuModel.exit();
            }
        });

        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
