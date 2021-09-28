package project_3;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Computer{
    private Deck deck;
    private GameLogic game;
    private ArrayList<Deck> computercards= new ArrayList<>();
    private ArrayDeque<Deck> computertempcard = new ArrayDeque<>();
    private Deck prioritycard;
    private Deck replacecard;
    private Queue<Deck> computermemory = new LinkedList<>();
    private boolean haspriority = false;
    
    public Computer(Deck deck, GameLogic game) {
        this.deck = deck;
        this.game = game;
        game.HandCards(computercards);
    }
    
    public ArrayList<Deck> getComputerCards() {
        return computercards;
    } 
    
    public ArrayDeque<Deck> getComputerTempCard() {
        return computertempcard;
    }
    
    public void ComputerPickCard() { // Function which helps the computer pick and discard a card.
        int pilechoice = (int) (Math.random() * 2);
        int cardchoice = (int) (Math.random() * 2);
        int deckcardchoice = (int) (Math.random() * 4);
        if (pilechoice == 0) { // Draw from draw pile;
            computertempcard.push(deck.getDrawPile().pop());
            if (cardchoice == 0) {
                deck.getDiscardPile().push(computertempcard.pop()); //Discard temp card to discard pile;
            } else {
                deck.getDiscardPile().push(computercards.get(deckcardchoice));
                computercards.set(deckcardchoice, computertempcard.pop()); // Discard one of the computer deck cards.
            }
        } else {
            computertempcard.push(deck.getDrawPile().pop());
            if (cardchoice == 0) {
                deck.getDiscardPile().push(computertempcard.pop()); //Discard temp card to discard pile;
            } else {
                deck.getDiscardPile().push(computercards.get(deckcardchoice));
                computercards.set(deckcardchoice, computertempcard.pop()); // Discard one of the computer deck cards.
            }
        }
    }
    
    public String AiResponse() { // Computer response to computer card button click.
        int random = (int) (Math.random() * 5);
        String response = "";
        switch(random) {
            case 0:
                response = "Sorry, I cant show you my card. :)";
                break;
            case 1:
                response = "Tell you what, you show me one of your cards and i'll show you one of mine, Deal?";
                break;
            case 2:
                response = "Nice try, Better luck next time.";
                break;
            case 3:
                response = "Really? are you that desperate to peek at my card. >_<";
                break;
            case 4:
                response = "No! No! No!, not gonna happen. -_-";
                break;
            default:
                response = "This Shouldn't happen.";
                break;
        }
        return response;
    }
    
    public void ComputerAI() { // Main Computer AI method.
        int tempindex;
        if (!computermemory.isEmpty() && prioritycard != null) {
            SacrificePriority(); // Temporary
        }
        // Choose which pile to draw card from
        if (haspriority) { // If priority card has been set.
            if (!(!deck.getDiscardPile().isEmpty() || (deck.getDiscardPile().peek().getCardValue().equalsIgnoreCase(prioritycard.getCardValue())))) {
                computertempcard.push(deck.getDrawPile().pop());
                if (computertempcard.peek().getCardValue().equalsIgnoreCase(prioritycard.getCardValue())) {
                    DrawPriorityCard();
                } else {
                    deck.getDiscardPile().offer(computertempcard.pop());
                }
            } else {
                computertempcard.push(deck.getDiscardPile().pop());
                DrawPriorityCard();
            }
        } else { // Searching for priority card.
            computertempcard.push(deck.getDrawPile().pop());
            System.out.println("Comp Temp Card:" + computertempcard);
            if (!haspriority) {
                PriorityCard(computercards);
            }
            for (Deck checkcard : computercards) {
                System.out.println("Card Check:" +checkcard.getCardValue());
                if (checkcard.getCardValue().equalsIgnoreCase(computertempcard.peek().getCardValue())) {
//                    for (Deck replacecard : computercards) {
//                        if (!(replacecard.getCardValue().equalsIgnoreCase(checkcard.getCardValue()))) {
//                            deck.getDiscardPile().push(replacecard);
//                            computercards.set(computercards.indexOf(replacecard), computertempcard.pop());
//                            break;
//                        }
//                        break;
//                    }
                        replacecard = checkcard;
                        break;
                } else {
                    replacecard = null;
                }
            } 
            if (replacecard != null) {
                for (Deck replace : computercards) {
                        if(!(replace.getCardValue().equalsIgnoreCase(replacecard.getCardValue()))) {
                            tempindex = computercards.indexOf(replace);
                            deck.getDiscardPile().push(replace);
                            computercards.set(tempindex, computertempcard.pop());
                            break;
                        }
                }
            }
            if(!computertempcard.isEmpty()) {
                deck.getDiscardPile().push(computertempcard.pop());
            }
        }
        AiMemory();
        System.out.println("Priority Card:" +prioritycard);
    }
    
    public Deck PriorityCard(ArrayList computerdeck) { // Method to set Priority card
        ArrayList<Deck> mirrorlist = new ArrayList<>();
        int oldcount = 0;
        int count = 0;
        
        for(Deck deckcard : computercards) {
            mirrorlist.add(deckcard);
        }
        for(Deck computercard : computercards) {
            for(Deck mirrorcard : mirrorlist) {
                if(mirrorcard.getCardValue().equalsIgnoreCase(computercard.getCardValue())
                        || computertempcard.peek().getCardValue().equalsIgnoreCase(computercard.getCardValue())) {
                    count++;
                }
            }
            if(count > oldcount && count != 1) {
                prioritycard = computercard;
                haspriority = true;
            } else {
                haspriority = false;
            }
            count = 0;
        }
        mirrorlist.clear();
        return prioritycard;
    }
    
    public void SacrificePriority() { // Method to remove a priority card based on the chances of getting a priority card.
        for(Deck carddeck : computermemory) {
            if((carddeck.getCardValue().equalsIgnoreCase(prioritycard.getCardValue()) || CardProbability(prioritycard) < 0.5)
                    && !carddeck.getCardName().equalsIgnoreCase(deck.getDiscardPile().peek().getCardName())) {
                prioritycard = null;
                haspriority = false;
                break;
            }
        }
    }
    
    public void AiMemory() { // Computer temporarily retains memory of the last three discarded cards in the discard pile.
        if(computermemory.size() < 3) {
            computermemory.offer(deck.getDiscardPile().peek());
        } else {
            computermemory.poll();
            computermemory.offer(deck.getDiscardPile().peek());
        }
    }
    
    public double CardProbability(Deck cardprobability) { //Get the probability of getting a priority card.
        double probability = 0.0;
        int cardcount = 0;
        
        for(Deck deckcard : computercards) {
            if(deckcard.getCardValue().equalsIgnoreCase(cardprobability.getCardValue())) {
                cardcount++;
            }
        }
        return probability = cardcount/4.0;
    }
    
    public void DrawPriorityCard() { // Dont discard cards whose value is equal to priority card.
        int tempcard;
        for (Deck checkcard : computercards) {
                if (!(checkcard.getCardValue().equalsIgnoreCase(prioritycard.getCardValue()))) {
                    tempcard = computercards.indexOf(checkcard);
                    deck.getDiscardPile().push(checkcard);
                    computercards.set(tempcard, computertempcard.pop());
                    break;
                }
            }
    }

}
