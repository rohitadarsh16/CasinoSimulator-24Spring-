import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class RandnumModel {
    /**
     * Current state of the game.
     */
    private currentState currentState;

    private MainMenuModel menuModel;
    private RandnumView randnumView;

    private int cards_played; // how many cards were removed from the deck so far

    /**
     * The deck of cards represented as a linked list.
     */
    private LinkedList<Card> deck;
    /**
     * Player object that represents the dealer.
     */
    private Player dealer;
    /**
     * Player object that represents the player.
     */
    private Player player;
    /**
     * Current balance.
     */
    private int money;
    /**
     * Current bet.
     */
    private int bet;
    public enum currentState{
        win, guess
    }
    public currentState getCurrentState(){
        return currentState;
    }

    public RandnumModel(MainMenuModel menu, int money) {
        menuModel = menu;
        deck = new LinkedList<>();
        dealer = new RandnumModel.Player();
        player = new RandnumModel.Player();
        randnumView = new BlackjackView(this);
        this.money = money;
        bet = 0;

        RandnumView.UpdateBalance();
        createDeck();
        shuffle();
    }
    /**
     * createDeck method.
     * Create the deck; a linked list of 52 Cards of each suit is initialized.
     */
    public void createDeck() {
        deck.clear();
        int suit; // 0 - clubs; 1 - diamonds; 2 - hearts; 3 - spades
        int points = 0; // point value for each card

        for (int i = 0; i < 4; i++) {
            suit = i;
            for (int j = 0; j < 13; j++) {
                points = j + 1; // 2 to 9 are face value
                if (j >= 10) // 10, jack, queen, king are 10 points
                    points = 10;
                Card c = new Card(j+1, suit, points); // first card is Ace
                deck.add(c);
            }
        }
    }
    /**
     * shuffle method.
     * Shuffle the deck according to the difficulty level.
     */
    public void shuffle() {
        Random randomNumber = new Random();
        Card ace = new Card(1, 0, 1);
        Collections.shuffle(deck);
        if(MainMenuView.difficulty == "Medium") {
            int index = randomNumber.nextInt(8);
            if(index == 0) {//If you pass a 1/8 chance
                for(int i = 0; i < 52; i++) {
                    if(deck.get(i).value == 1) {
                        ace = deck.get(i);
                    }
                }
                int indexAce = deck.indexOf(ace);
                Card firstCard = deck.getFirst();
                deck.set(indexAce, firstCard); //Swap the first card with the ace
                deck.set(0, ace);
            }
        }
        else if (MainMenuView.difficulty == "Easy") { //If the difficulty is easy
            int index = randomNumber.nextInt(6);
            if(index == 0) {//If you pass a 1/6 chance
                for(int i = 0; i < deck.size(); i++) {
                    if(deck.get(i).value == 1) {
                        ace = deck.get(i);
                    }
                }
                int indexAce = deck.indexOf(ace);
                Card firstCard = deck.getFirst();
                deck.set(indexAce, firstCard); //Swap the first card with the ace
                deck.set(0, ace);
            }
        }
    }
    /**
     * win method.
     * Update BlackjackView, reset players and deck.
     */
    public void win() {
        switch (currentState) {
            case win -> randnumView.ShowPlayerWin();
            default -> randnumView.ShowPlayerLose();
        }

        dealer.reset();
        player.reset();
        createDeck();
        shuffle(); //Shuffle the deck after every turn
    }
    /**
     * playerWins method.
     * Update bet and balance in BlackjackView, reset bet.
     */
    public void playerWins(){
        money = money + (bet * 2);
        bet = 0;
        randnumView.UpdateBalance();
        randnumView.UpdateBet();
    }
    /**
     * playerLoses method.
     * Update bet and balance in BlackjackView.
     */
    public void playerLoses(){
        bet = 0;
        randnumView.UpdateBalance();
        randnumView.UpdateBet();
    }
    /**
     * playerBet method.
     * Player places a bet.
     */
    public void playerBet(int bet) {
        if (money >= bet) {
            this.bet += bet;
            money -= bet;
            randnumView.UpdateBalance();
            randnumView.UpdateBet();
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

    public void exit() {
        MainMenuModel.money = money;
        menuModel.setVisible();
    }
    private class Card {
        int value;
        int suit;
        int points;

        /**
         * Constructor.
         * Initialize a card object.
         * @param v The face value of the card.
         * @param s The suit of the card.
         * @param p The number of points for the card.
         */
        Card(int v, int s, int p) {
            value = v;
            suit = s;
            points = p;
        }

        /**
         * Constructor.
         * Initialize a card object.
         * @param c A card object.
         */
        Card(Card c) {
            value = c.value;
            suit = c.suit;
            points = c.points;
        }
    }

    /**
     * Player class.
     * Used to define a player entity.
     */
    private class Player {
        Card[] hand;
        int i; // hand index
        int j; // index for popping
        int total;
        private boolean stand;

        /**
         * Constructor.
         * Initialize a player object; creates a hand which is
         * an array of Cards.
         */
        Player() {
            stand = false;
            hand = new Card[1];
            i = j = total = 0;
        }

        /**
         * addCard method.
         * Add a card to the hand; if all cards are dealt reset the deck.
         */
        void addCard() {
            // if all cards are dealt create a new deck and shuffle before continuing
            //int cardCount = 0;
            if (cards_played == 52) {
                createDeck();
                shuffle();
                cards_played = 0;
            }
            Card c = new Card(deck.pollFirst()); // get top card from deck
            cards_played++;
            hand[i++] = c;
            if (c.points == 1 && total < 11) { //if card is ace add 10 points as long as it won't cause player to bust
                total += c.points + 10;
            } else {                           // otherwise all cards are normal values
                total += c.points;
            }
        }

        /**
         * getHandCount method.
         * @return The size of the hand as an integer.
         */
        public int getHandCount() { return i; }

        /**
         * hasBusted method.
         * @return True if the total points are over 21.
         */
        public boolean hasBusted() { return total > 21; }

        /**
         * isBlackjack method.
         * @return True if the total points add up to 21.
         */
        public boolean isBlackjack() { return total == 21; }

        /**
         * isStanding method.
         * @return True is player is standing.
         */
        public boolean isStanding() { return stand; }

        /**
         * setToStanding method.
         * @param stand Boolean variable; set to true if the player stands.
         */
        public void setToStanding(boolean stand) { this.stand = stand; }

        /**
         * popCard method.
         * @return The top Card object in the hand.
         */
        Card popCard() { return hand[j++]; }

        /**
         * getTotal method.
         * @return Total points as an integer.
         */
        int getTotal() { return total; }

        /**
         * reset method.
         * Resets the player's points and hand.
         */
        void reset() {
            i = j = total = 0;
            stand = false;
        }
    }

}
