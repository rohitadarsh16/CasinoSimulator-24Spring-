public class MainMenuModel {
    private MainMenuView menuView;
    private SlotModel slotModel;
    private BlackjackModel blackjackModel;

    private int money; // available money to play

    public MainMenuModel() {
        money = 100;
        menuView = new MainMenuView(this);
    }

    /**
     * Make MainMenu window visible.
     */
    public void setVisible() {
        menuView.setVisible(true);
    }

    public void startSlot() {
        slotModel = new SlotModel(this, money);
    }

    public void startBlackjack() {
        blackjackModel = new BlackjackModel(this, money);
    }

    public void exit() {
        System.exit(0);
    }
}
