public class SlotModel {
    private MainMenuModel menuModel;
    private SlotView slotView;
    private int money;

    public SlotModel(MainMenuModel menu, int money) {
        menuModel = menu;
        this.money = money;
        slotView = new SlotView(this);
    }

    public void exit() {
        menuModel.setVisible();
    }
}
