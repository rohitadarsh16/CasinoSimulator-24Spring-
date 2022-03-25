import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BlackjackModel {
    private MainMenuModel menuModel;
    private BlackjackView blackjackView;
    private int money;
    private LinkedList<Card> deck;
    private Player dealer;
    private Player player;

    public BlackjackModel(MainMenuModel menu, int money) {
        menuModel = menu;
        this.money = money;
        deck = new LinkedList<>();
        dealer = new Player();
        player = new Player();
        blackjackView = new BlackjackView(this);
    }

    public void createDeck() {
        deck.clear();
        int suit; // 0 - clubs; 1 - diamonds; 2 - hearts; 3 - spades

        for (int i = 0; i < 4; i++) {
            suit = i;
            for (int j = 1; j <= 13; j++) {
                Card c = new Card(j, suit);
                deck.add(c);
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public void deal() {
        for (int i = 0; i < 4; i++) {
            if (i % 2 == 0)
                player.addCard();
            else
                dealer.addCard();
        }
    }

    int[] popDealerCard() {
        int[] ret = new int[2];
        Card c = dealer.popCard();

        ret[0] = c.value;
        ret[1] = c.suit;

        return ret;
    }

    int[] popPlayerCard() {
        int[] ret = new int[2];
        Card c = player.popCard();

        ret[0] = c.value;
        ret[1] = c.suit;

        return ret;
    }

    public void exit() {
        menuModel.setVisible();
    }

    // DEBUG
    public void printDeck() {
        deck.forEach(element -> System.out.println(element.value + " " + element.suit + "\n"));
    }

    private class Card {
        int value;
        int suit;

        Card(int v, int s) {
            value = v;
            suit = s;
        }

        Card(Card c) {
            value = c.value;
            suit = c.suit;
        }
    }

    private class Player {
        Card[] hand;
        int i; // hand index
        int j; // index for popping
        int total;

        Player() {
            hand = new Card[10];
            i = j = total = 0;
        }

        void addCard() {
            Card c = new Card(deck.pollFirst());
            hand[i++] = c;
            total += c.value;
        }

        Card popCard() { return hand[j++]; }

        int getTotal() { return total; }
    }
}
