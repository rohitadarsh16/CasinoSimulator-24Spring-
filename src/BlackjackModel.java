public class BlackjackModel {
    private MainMenuModel menuModel;
    private BlackjackView blackjackView;
    private int money;

    public BlackjackModel(MainMenuModel menu, int money) {
        menuModel = menu;
        this.money = money;
        blackjackView = new BlackjackView(this);
    }

    public void exit() {
        menuModel.setVisible();
    }
}
