import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BlackjackModel {
    private MainMenuModel menuModel;
    private BlackjackView blackjackView;
    private LinkedList<Card> deck;
    private Player dealer;
    private Player player;

    private int money;
    private int round;

    public BlackjackModel(MainMenuModel menu, int money) {
        menuModel = menu;
        deck = new LinkedList<>();
        dealer = new Player();
        player = new Player();
        blackjackView = new BlackjackView(this);
        this.money = money;
        round = 1;
    }

    public void createDeck() {
        deck.clear();
        int suit; // 0 - clubs; 1 - diamonds; 2 - hearts; 3 - spades
        int points = 0;

        for (int i = 0; i < 4; i++) {
            suit = i;
            for (int j = 0; j < 13; j++) {
                points = j + 1;
                if (j >= 10)
                    points = 10;
                Card c = new Card(j+1, suit, points);
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
        int[] ret = new int[3];
        Card c = dealer.popCard();

        ret[0] = c.suit;
        ret[1] = c.value-1;
        ret[2] = c.points;

        return ret;
    }

    int[] popPlayerCard() {
        int[] ret = new int[3];
        Card c = player.popCard();

        ret[0] = c.suit;
        ret[1] = c.value-1;
        ret[2] = c.points;

        return ret;
    }

    int getDealerTotal() { return dealer.getTotal(); }
    int getPlayerTotal() { return player.getTotal(); }

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
        int points;

        Card(int v, int s, int p) {
            value = v;
            suit = s;
            points = p;
        }

        Card(Card c) {
            value = c.value;
            suit = c.suit;
            points = c.points;
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
            total += c.points;
        }

        Card popCard() { return hand[j++]; }

        int getTotal() { return total; }
    }
}
