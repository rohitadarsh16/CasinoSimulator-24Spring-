import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuView extends JFrame {
    private MainMenuModel menuModel;
    public static String gamemode;
    private JButton blackBtn;
    private JComboBox<String> jComboBox;

    public MainMenuView(MainMenuModel menu) {
        super("CasinoSimulator");
        menuModel = menu;

        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JLabel jLabel = new JLabel();
        jLabel.setBounds(100, 0, 100, 60);
        jLabel.setText("Gamemode: ");
        String[] playOptions = {"Freeplay", "Simulated Casino"};
        jComboBox = new JComboBox<>(playOptions);
        jComboBox.setBounds(190, 0, 100, 60);
        add(jComboBox);
        add(jLabel);

        JButton slotBtn = new JButton("Slot Machine");
        slotBtn.setBounds(100, 50, 100, 100);
        add(slotBtn);

        slotBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamemode = jComboBox.getItemAt(jComboBox.getSelectedIndex());
                setVisible(false);
                menuModel.startSlot();
            }
        });

        blackBtn = new JButton("Blackjack");
        blackBtn.setBounds(100, 150, 100, 100);
        add(blackBtn);

        blackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamemode = jComboBox.getItemAt(jComboBox.getSelectedIndex());
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

    /**
     * For testing
     */
    public JButton getBlackButton() { return blackBtn; }
    public JComboBox<String> getGameOptions() { return jComboBox; }
}
