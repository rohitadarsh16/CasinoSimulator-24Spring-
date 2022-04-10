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
        assertEquals("Should have 52 cards in the deck", 52, blackjackModel.getDeckCount());
    }
}
