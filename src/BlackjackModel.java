import java.util.Collections;
import java.util.LinkedList;

public class BlackjackModel {
    private currentState currentState;
    private MainMenuModel menuModel;
    private BlackjackView blackjackView;
    private LinkedList<Card> deck;
    private Player dealer;
    private Player player;

    private int money;
    private int bet;
    private int cards_played; // how many cards were removed from the deck so far
    private int cardsPerRound; //counts number of cards played per round

    private int[] hidden_card; // dealer's hidden card

    public enum currentState{
        pWin, dWin, pStand, dStand, dTurn, pTurn, draw //d = dealer, p = player
    }

    public currentState getCurrentState(){
        return currentState;
    }

    // FOR TESTING
    private boolean done;

    public BlackjackModel(MainMenuModel menu, int money) {
        currentState = currentState.pTurn;
        menuModel = menu;
        deck = new LinkedList<>();
        dealer = new Player();
        player = new Player();
        blackjackView = new BlackjackView(this);
        this.money = money;
        bet = 0;
        cards_played = 0;

        blackjackView.UpdateBalance();
        createDeck();
        shuffle();
    }

    /**
     * Create the deck; a list of 52 Cards of each suit.
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
     * Shuffle the deck.
     */
    public void shuffle() {
        Collections.shuffle(deck);
    }

    /**
     * Player hits.
     */
    public void playerHit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                player.addCard();
                int[] c = popPlayerCard();
                blackjackView.ShowPlayerCard(c);

                if (player.isBlackjack()) {
                    currentState = currentState.pWin; //player wins
                    blackjackView.ShowDealerCard(hidden_card);
                    blackjackView.ShowBackCard(false);
                    playerWins();
                    win();
                }
                else if (player.hasBusted()) {
                    currentState = currentState.dWin; //dealer wins
                    blackjackView.ShowDealerCard(hidden_card);
                    blackjackView.ShowBackCard(false);
                    playerLoses();
                    win();
                }
                //else {
                //currentState = currentState.dTurn;
                //}
            }
        }).start();
    }

    /**
     * Player stands.
     */
    public void playerStand() {              //player stands which makes it the dealer's turn
        new Thread(new Runnable() {
            @Override
            public void run() {
                currentState = currentState.dTurn;
                player.setToStanding(true);
                blackjackView.ShowDealerCard(hidden_card);
                blackjackView.ShowBackCard(false);
                dealerTurn();
            }
        }).start();
    }

    /**
     * Player doubles down (doubles bet but only gets one more card)
     */
    public void doubleDown(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                money = money - bet;
                bet = bet + bet;
                player.addCard();
                int[] c = popPlayerCard();
                blackjackView.ShowPlayerCard(c);

                if (player.isBlackjack()) {
                    currentState = currentState.pWin; //player wins
                    blackjackView.ShowDealerCard(hidden_card);
                    blackjackView.ShowBackCard(false);
                    playerWins();
                    win();
                }
                else if (player.hasBusted()) {
                    currentState = currentState.dWin; //dealer wins
                    blackjackView.ShowDealerCard(hidden_card);
                    blackjackView.ShowBackCard(false);
                    playerLoses();
                    win();
                }
                //playerStand();
            }
        }).start();
    }

    /**
     * Dealer hits.
     */
    public void dealerHit() {
        dealer.addCard();
        int[] c = popDealerCard();
        blackjackView.ShowDealerCard(c);

        if(dealer.isBlackjack()) {
            currentState = currentState.dWin; //dealer has blackjack and wins
            playerLoses();
            win();
        }
        else if(dealer.hasBusted()) {
            currentState = currentState.pWin; //dealer busted and player wins
            playerWins();
            win();
        }
        else if (player.isStanding()) { //dealer neither has blackjack nor busted and loops back to check if at 17
            dealerTurn();
        }
//        else {
//            currentState = currentState.pTurn;
//        }
    }

    /**
     * Dealer stands.
     */
    public void dealerStand() {
        currentState = currentState.pTurn;
        dealer.setToStanding(true);                         // if both player and dealer are standing then do this
        if (player.isStanding()) {
            if (player.getTotal() == dealer.getTotal()) {     //player and dealer have same total, draw
                currentState = currentState.draw;
                playerDraws();
            }
            else if (player.getTotal() < dealer.getTotal()) {     //dealer has higher total than player, dealer wins
                currentState = currentState.dWin;
                playerLoses();
            }
            else {                                               //otherwise player has higher total and wins
                currentState = currentState.pWin;
                playerWins();
            }
            win();
        }
    }

    /**
     * Dealer's turn.
     */
    public void dealerTurn() {               //dealer's turn
        if (dealer.getTotal() < 17) {        //if dealer has less than 17 they hit
            dealerHit();
        }
        else                                //otherwise they stand
            dealerStand();
    }

    /**
     * Update BlackjackView and reset players.
     */
    public void win() {
        switch (currentState) {
            case pWin -> blackjackView.ShowPlayerWin();
            case dWin -> blackjackView.ShowDealerWin();
            case draw -> blackjackView.ShowDraw();
        }

        dealer.reset();
        player.reset();
    }

    /**
     * Update bet and money balance where player wins
     */
    public void playerWins(){
        money = money + (bet * 2);
        bet = 0;
        blackjackView.UpdateBalance();
        blackjackView.UpdateBet();
        cardsPerRound = 0;
    }

    /**
     * Update bet and money balance where player loses
     */
    public void playerLoses(){
        bet = 0;
        blackjackView.UpdateBalance();
        blackjackView.UpdateBet();
        cardsPerRound = 0;
    }

    /**
     * Update bet and money balance where player draws
     */
    public void playerDraws(){
        money = money + bet;
        bet = 0;
        blackjackView.UpdateBalance();
        blackjackView.UpdateBet();
        cardsPerRound = 0;
    }

    /**
     * Player places a bet.
     */
    public void playerBet(int bet) {
        if (money >= bet) {
            this.bet += bet;
            money -= bet;
            blackjackView.UpdateBalance();
            blackjackView.UpdateBet();
        }
    }

    /**
     * Initial deal of cards. The order is player, dealer, player, dealer.
     * The dealer's last card is face down.
     */
    public void deal() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                done = false; // for testing
                int[] c;
                for (int i = 0; i < 4; i++) {
                    if (i % 2 == 0) {
                        player.addCard();
                        c = popPlayerCard();
                        blackjackView.ShowPlayerCard(c);
                    }
                    else {
                        dealer.addCard();
                        c = popDealerCard();
                        if (i < 3)
                            blackjackView.ShowDealerCard(c);
                        else { // dealer's last card is face down
                            hidden_card = c;
                            blackjackView.ShowBackCard(true);
                        }
                    }
                }
                done = true; // for testing
                if (player.isBlackjack()) {
                    currentState = currentState.pWin; //player wins
                    blackjackView.ShowDealerCard(hidden_card);
                    blackjackView.ShowBackCard(false);
                    playerWins();
                    win();
                }
            }
        }).start();
    }

    /**
     * Get dealer's top card.
     * @return The card's suit, value, and points in an integer array.
     */
    int[] popDealerCard() {
        int[] ret = new int[3];
        Card c = dealer.popCard();

        ret[0] = c.suit;
        ret[1] = c.value-1;
        ret[2] = c.points;

        return ret;
    }

    /**
     * Get player's top card.
     * @return The card's suit, value, and points in an integer array.
     */
    int[] popPlayerCard() {
        int[] ret = new int[3];
        Card c = player.popCard();

        ret[0] = c.suit;
        ret[1] = c.value-1; // subtract 1 because index of array starts at 0
        ret[2] = c.points;

        return ret;
    }

    /*
     * Get current numbers.
     */
    public int getDealerTotal() { return dealer.getTotal(); }
    public int getPlayerTotal() { return player.getTotal(); }
    public int getBalance() { return money; }
    public int getBet() { return bet; }

    /**
     * Quit game.
     */
    public void exit() {
        MainMenuModel.money = money;
        menuModel.setVisible();
    }

    // DEBUG
    public void printDeck() {
        deck.forEach(element -> System.out.println(element.value + " " + element.suit + "\n"));
    }

    /**
     * Card class; represents a card.
     */
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

    /**
     * Player class.
     */
    private class Player {
        Card[] hand;
        int i; // hand index
        int j; // index for popping
        int total;
        private boolean stand;

        Player() {
            stand = false;
            hand = new Card[10]; // max of 10 possible cards should be enough
            i = j = total = 0;
        }

        /**
         * Add card to the hand.
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

        public int getHandCount() { return i; }

        public boolean hasBusted() { return total > 21; }

        public boolean isBlackjack() { return total == 21; }

        public boolean isStanding() { return stand; }

        public void setToStanding(boolean stand) { this.stand = stand; }

        Card popCard() { return hand[j++]; }

        int getTotal() { return total; }

        void reset() {
            i = j = total = 0;
            stand = false;
        }
    }

    /**
     * For testing
     */
    public int getDeckCount() { return deck.size(); }
    public int getPlayerCardCount() { return player.getHandCount(); }
    public int getDealerCardCount() { return dealer.getHandCount(); }
    public BlackjackView getView() { return blackjackView; }
    public boolean isDone() { return done; }
}
