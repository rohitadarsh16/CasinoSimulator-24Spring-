import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BlackjackModel {
    private currentState currentState;
    private MainMenuModel menuModel;
    private BlackjackView blackjackView;
    private LinkedList<Card> deck;
    private Player dealer;
    private Player player;

    private int money;
    private int round;

    public enum currentState{
        pWin, dWin, pStand, dStand, dTurn, pTurn, draw //d = dealer, p = player
    }

    public currentState getCurrentState(){
        return currentState;
    }

    public BlackjackModel(MainMenuModel menu, int money) {
        currentState = currentState.pTurn;
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

    public void playerHit(){
        player.addCard();
        if (player.isBlackjack()){
            currentState = currentState.pWin; //player wins
        }
        else if(player.hasBusted()){
            currentState = currentState.dWin; //dealer wins
        }
        else{
            currentState = currentState.dTurn;
        }
    }

    public void playerStand(){              //player stands which makes it the dealer's turn
        currentState = currentState.dTurn;
        player.setToStanding(true);
        dealerTurn();
    }

    public void dealerHit(){
        dealer.addCard();
        if(dealer.isBlackjack()){
            currentState = currentState.dWin; //dealer has blackjack and wins
        }
        else if(dealer.hasBusted()){
            currentState = currentState.pWin; //dealer busted and player wins
        }
        else if (player.isStanding()){ //dealer neither has blackjack or busted and loops back to check if at 17
            dealerTurn();
        }
        else {
            currentState = currentState.pTurn;
        }
    }

    public void dealerStand(){
        currentState = currentState.pTurn;
        dealer.setToStanding(true);                         // if both player and dealer are standing then do this
        if(player.isStanding()){
            if(player.getTotal() == dealer.getTotal()){     //player and dealer have same total, draw
                currentState = currentState.draw;
            }
            else if(player.getTotal() < dealer.getTotal()){     //dealer has higher total than player, dealer wins
                currentState = currentState.dWin;
            }
            else{                                               //otherwise player has higher total and wins
                currentState = currentState.pWin;
            }
        }
    }

    public void dealerTurn(){               //dealer's turn
        if (dealer.getTotal() < 17){        //if dealer has less than 17 they hit
            dealerHit();
        }
        else                                //otherwise they stand
            dealerStand();
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
        MainMenuModel.money = money;
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
        private boolean stand;

        Player() {
            stand = false;
            hand = new Card[10];
            i = j = total = 0;
        }

        void addCard() {
            Card c = new Card(deck.pollFirst());
            hand[i++] = c;
            total += c.points;
        }

        public boolean hasBusted(){
            return total > 21;
        }

        public boolean isBlackjack(){
            return total == 21;
        }

        public boolean isStanding(){
            return stand;
        }

        public void setToStanding(boolean stand){
             this.stand = stand;
        }

        Card popCard() { return hand[j++]; }

        int getTotal() { return total; }
    }
}
