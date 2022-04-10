import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class BlackjackTest {
    BlackjackModel blackjackModel;

    @Before
    public void setup() {
        blackjackModel = new BlackjackModel(null, 100);
    }

    @Test
    public void deckHas52Cards() {
        assertEquals("Should have 52 cards in the deck.", 52, blackjackModel.getDeckCount());
    }

    @Test
    public void afterDealPlayerHas2Cards() {
        BlackjackView b = blackjackModel.getView();
        b.getDealBtn().doClick();
        while (!blackjackModel.isDone()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }
        assertEquals("Should have 2 cards in hand.", 2, blackjackModel.getPlayerCardCount());
    }

    @Test
    public void afterDealDealerHas2Cards() {
        BlackjackView b = blackjackModel.getView();
        b.getDealBtn().doClick();
        while (!blackjackModel.isDone()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }
        assertEquals("Should have 2 cards in hand.", 2, blackjackModel.getDealerCardCount());
    }
}
