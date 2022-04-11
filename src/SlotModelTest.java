//import org.junit.Test;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SlotModelTest {
    @Test
    public void firstRowSameSymbolShouldReturnWinner() {
        SlotModel slotModel = new SlotModel(new MainMenuModel(), 100);
        slotModel.setSlot(0, 0);
        slotModel.setSlot(1, 0);
        slotModel.setSlot(2, 0);
        assertEquals("Winner!!", slotModel.matchCheck());

    }

    //Some how the test is failing (reward 105 instead of 102 on normal symbol).
    //Leave it for now as the match-check method will be edited for difficulty.
    @Test
    public void oneRowSameSymbolShouldWin2Dollar() {
        SlotModel slotModel = new SlotModel(new MainMenuModel(), 100);
        slotModel.setSlot(0, 5);
        slotModel.setSlot(1, 5);
        slotModel.setSlot(2, 5);
        slotModel.matchCheck();
        assertEquals(102, slotModel.getMoney());
    }

}