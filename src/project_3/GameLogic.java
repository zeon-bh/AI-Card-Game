package project_3;

import java.util.ArrayList;
import java.util.Collections;

public class GameLogic{
    Deck deck;
    public GameLogic(Deck deck) {
        this.deck = deck;
        deck.NewCardDeck();
        deck.DrawPile();
    }
    
    public void HandCards(ArrayList cards) { // Function to hand out cards to player and computer.
        for(int i = 0; i < 4; i++) {
            cards.add(deck.getDrawPile().pop());
        }
    }
    
    public boolean hasWon(ArrayList<Deck> carddeck) { // Function to check whether the player or the computer has won the game.
        boolean haswon = false;
        String testcard = "";
        testcard = carddeck.get(0).getCardName();
        
        for(Deck checkcard : carddeck) {
            if (testcard.substring(0, (testcard.length() - 1)).equalsIgnoreCase(checkcard.getCardName().substring(0, (checkcard.getCardName().length() - 1)))) {
                haswon = true;
            } else {
                haswon = false;
                break;
            }
        }
        return haswon;
    }
    public void ReShuffle() { // Re-shuffles the drawpile when all the cards are drawn.
            ArrayList<Deck> templist = new ArrayList<>();
            for(Deck carddeck : deck.getDiscardPile()) {
                templist.add(deck.getDiscardPile().pop());
            }
            Collections.shuffle(templist);
            for(Deck carddeck : templist) {
                deck.getDrawPile().push(carddeck);
            }
    }

}
