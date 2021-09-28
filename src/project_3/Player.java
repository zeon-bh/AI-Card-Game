package project_3;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Player{
    private GameLogic game;
    private Deck deck;
    private ArrayList<Deck> playercards = new ArrayList<>();
    private ArrayDeque<Deck> playertempcard = new ArrayDeque<>();
    
    public Player(Deck deck, GameLogic game) {
        this.game = game;
        this.deck = deck;
        game.HandCards(playercards);
    }
    
    public ArrayList<Deck> getPlayerCards() {
        return playercards;
    }
    
    public ArrayDeque<Deck> getTempCard() {
        return playertempcard;
    }
    
    public void PlayerDrawPile() { // Function to draw card from Draw pile to player deck.
            playertempcard.push(deck.getDrawPile().pop());
    }
    
    public void PlayerDiscardPile() { // Function to draw card from Discard pile to player deck.
            playertempcard.push(deck.getDiscardPile().pop());
    }
    
    public void PlayerDiscardCard(String card, int cardindex) { // Function to discard card.
        if(card.equalsIgnoreCase("playertempdeck") && cardindex == -1 && !playertempcard.isEmpty()) {
            deck.getDiscardPile().push(playertempcard.pop());
        } else if(card.equalsIgnoreCase("playerdeck")) {
            switch(cardindex) {
                case 0:
                    deck.getDiscardPile().push(playercards.get(cardindex));
                    playercards.set(cardindex, playertempcard.pop());
                    break;
                case 1:
                    deck.getDiscardPile().push(playercards.get(cardindex));
                    playercards.set(cardindex, playertempcard.pop());
                    break;
                case 2:
                    deck.getDiscardPile().push(playercards.get(cardindex));
                    playercards.set(cardindex, playertempcard.pop());
                    break;
                case 3:
                    deck.getDiscardPile().push(playercards.get(cardindex));
                    playercards.set(cardindex, playertempcard.pop());
                    break;
                default:
                    break;
            }
        }
    }

}
