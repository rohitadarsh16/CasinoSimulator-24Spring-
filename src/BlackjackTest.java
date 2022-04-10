import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.event.InputEvent;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class BlackjackTest {
    public MainMenuModel mainMenuModel;
    public MainMenuView mainMenuView;
    public BlackjackModel blackjackModel;

    @Before
    public void setup() {
        mainMenuModel = new MainMenuModel();
        mainMenuView = mainMenuModel.getView();
        mainMenuView.getGameOptions().setSelectedIndex(1);
        mainMenuView.getBlackButton().doClick();
        blackjackModel = mainMenuModel.getModel();
    }

    @Test
    public void deckHas52Cards() {
        assertEquals("Should have 52 cards in the deck.", 52, blackjackModel.getDeckCount());
    }

    @Test
    public void afterDealPlayerHas2Cards() {
        BlackjackView b = blackjackModel.getView();
        b.getDealBtn().doClick();
        while (!blackjackModel.isDoneDeal()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }
        assertEquals("Player should have 2 cards in hand.", 2, blackjackModel.getPlayerCardCount());
    }

    @Test
    public void afterDealDealerHas2Cards() {
        BlackjackView b = blackjackModel.getView();
        b.getDealBtn().doClick();
        while (!blackjackModel.isDoneDeal()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }
        assertEquals("Dealer should have 2 cards in hand.", 2, blackjackModel.getDealerCardCount());
    }

    @Test
    public void bet15Dollars() throws AWTException {
        try { Thread.sleep(1000); }
        catch (InterruptedException e) {}
        Robot bot = new Robot();
        int mask = InputEvent.BUTTON1_DOWN_MASK;
        bot.mouseMove(0, 0);
        bot.mouseMove(479, 592);
//        bot.mousePress(mask);
//        bot.mouseRelease(mask);
//        bot.mouseMove(550, 587);
//        bot.mousePress(mask);
//        bot.mouseRelease(mask);
        assertEquals("Amount bet should be $15.", 15, blackjackModel.getBet());
    }

    @Test
    public void cardAddedAfterHit(){
        BlackjackView b = blackjackModel.getView();
        b.getDealBtn().doClick();
        b.getHitBtn().doClick();
        while (!blackjackModel.isDoneHit()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }
        assertEquals("Should add one card", 1, blackjackModel.getHitCount());
    }


/*
    @Test
    public void playerStandEndsGame(){
        BlackjackView b = blackjackModel.getView();
        b.getDealBtn().doClick();
        //b.getStandBtn().doClick();
        while (!blackjackModel.isDoneStand()) {
            try {
                Thread.sleep(2000);
                b.getStandBtn().doClick();

                //b.getStandBtn().doClick();
            } catch (InterruptedException e) {}
        }
        assertEquals("dTurn", blackjackModel.getCurrentState());
    }


 */

/*
    @Test
    public void moneyIsGainedAfterWin() {
        int temp = blackjackModel.getBalance();
        BlackjackView b = blackjackModel.getView();
        b.getDealBtn().doClick();
        System.out.println(blackjackModel.getBalance());
       // b.getStandBtn().doClick();
        while (!blackjackModel.isDoneStand()) {
            try {
                Thread.sleep(2000);
                b.getStandBtn().doClick();
            } catch (InterruptedException e) {}
        }
        if(blackjackModel.getCurrentState().equals("pWin")){
            System.out.println(blackjackModel.getBalance());
            assertTrue(blackjackModel.getBalance() > temp);
        }
        else if(blackjackModel.getCurrentState().equals("dWin")){
            System.out.println(blackjackModel.getBalance());
            assertTrue(blackjackModel.getBalance() < temp);
        }
        else{
            System.out.println(blackjackModel.getBalance());
            assertTrue(blackjackModel.getBalance() == temp);
        }
    }

*/


}
