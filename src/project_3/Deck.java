package project_3;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private String cardname;
    private File cardfile;
    private final ArrayList<Deck> carddeck = new ArrayList<>();
    private ArrayDeque<Deck> drawpile = new ArrayDeque<>();
    private ArrayDeque<Deck> discardpile = new ArrayDeque<>();
    
    public Deck(String cardname, File cardfile) {
        this.cardname = cardname;
        this.cardfile = cardfile;
    }
    
    /******************************* Getters **********************************/
    
    public String getCardName() {
        return cardname;
    }
    
    public File getCardFile() {
        return cardfile;
    }
    
    public ArrayDeque<Deck> getDrawPile() {
        return drawpile;
    }
    
    public ArrayDeque<Deck> getDiscardPile() {
        return discardpile;
    }
    
    public String getCardValue() {
        return cardname.substring(0, cardname.length()-1);
    }
    
    /******************************* The Card Decks **********************************/
    
    protected void NewCardDeck()
    {
        // Clubs
        carddeck.add(new Deck("2c",(new File("Cards/2_of_clubs.png"))));
        carddeck.add(new Deck("3c",(new File("Cards/3_of_clubs.png"))));
        carddeck.add(new Deck("4c",(new File("Cards/4_of_clubs.png"))));
        carddeck.add(new Deck("5c",(new File("Cards/5_of_clubs.png"))));
        carddeck.add(new Deck("6c",(new File("Cards/6_of_clubs.png"))));
        carddeck.add(new Deck("7c",(new File("Cards/7_of_clubs.png"))));
        carddeck.add(new Deck("8c",(new File("Cards/8_of_clubs.png"))));
        carddeck.add(new Deck("9c",(new File("Cards/9_of_clubs.png"))));
        carddeck.add(new Deck("10c",(new File("Cards/10_of_clubs.png"))));
        carddeck.add(new Deck("jc",(new File("Cards/jack_of_clubs.png"))));
        carddeck.add(new Deck("qc",(new File("Cards/queen_of_clubs.png"))));
        carddeck.add(new Deck("kc",(new File("Cards/king_of_clubs.png"))));
        carddeck.add(new Deck("ac",(new File("Cards/ace_of_clubs.png"))));
        
        //Diamonds
        carddeck.add(new Deck("2d",(new File("Cards/2_of_diamonds.png"))));
        carddeck.add(new Deck("3d",(new File("Cards/3_of_diamonds.png"))));
        carddeck.add(new Deck("4d",(new File("Cards/4_of_diamonds.png"))));
        carddeck.add(new Deck("5d",(new File("Cards/5_of_diamonds.png"))));
        carddeck.add(new Deck("6d",(new File("Cards/6_of_diamonds.png"))));
        carddeck.add(new Deck("7d",(new File("Cards/7_of_diamonds.png"))));
        carddeck.add(new Deck("8d",(new File("Cards/8_of_diamonds.png"))));
        carddeck.add(new Deck("9d",(new File("Cards/9_of_diamonds.png"))));
        carddeck.add(new Deck("10d",(new File("Cards/10_of_diamonds.png"))));
        carddeck.add(new Deck("jd",(new File("Cards/jack_of_diamonds.png"))));
        carddeck.add(new Deck("qd",(new File("Cards/queen_of_diamonds.png"))));
        carddeck.add(new Deck("kd",(new File("Cards/king_of_diamonds.png"))));
        carddeck.add(new Deck("ad",(new File("Cards/ace_of_diamonds.png"))));
        
        //Hearts
        carddeck.add(new Deck("2h",(new File("Cards/2_of_hearts.png"))));
        carddeck.add(new Deck("3h",(new File("Cards/3_of_hearts.png"))));
        carddeck.add(new Deck("4h",(new File("Cards/4_of_hearts.png"))));
        carddeck.add(new Deck("5h",(new File("Cards/5_of_hearts.png"))));
        carddeck.add(new Deck("6h",(new File("Cards/6_of_hearts.png"))));
        carddeck.add(new Deck("7h",(new File("Cards/7_of_hearts.png"))));
        carddeck.add(new Deck("8h",(new File("Cards/8_of_hearts.png"))));
        carddeck.add(new Deck("9h",(new File("Cards/9_of_hearts.png"))));
        carddeck.add(new Deck("10h",(new File("Cards/10_of_hearts.png"))));
        carddeck.add(new Deck("jh",(new File("Cards/jack_of_hearts.png"))));
        carddeck.add(new Deck("qh",(new File("Cards/queen_of_hearts.png"))));
        carddeck.add(new Deck("kh",(new File("Cards/king_of_hearts.png"))));
        carddeck.add(new Deck("ah",(new File("Cards/ace_of_hearts.png"))));
        
        //Spades
        carddeck.add(new Deck("2s",(new File("Cards/2_of_spades.png"))));
        carddeck.add(new Deck("3s",(new File("Cards/3_of_spades.png"))));
        carddeck.add(new Deck("4s",(new File("Cards/4_of_spades.png"))));
        carddeck.add(new Deck("5s",(new File("Cards/5_of_spades.png"))));
        carddeck.add(new Deck("6s",(new File("Cards/6_of_spades.png"))));
        carddeck.add(new Deck("7s",(new File("Cards/7_of_spades.png"))));
        carddeck.add(new Deck("8s",(new File("Cards/8_of_spades.png"))));
        carddeck.add(new Deck("9s",(new File("Cards/9_of_spades.png"))));
        carddeck.add(new Deck("10s",(new File("Cards/10_of_spades.png"))));
        carddeck.add(new Deck("js",(new File("Cards/jack_of_spades.png"))));
        carddeck.add(new Deck("qs",(new File("Cards/queen_of_spades.png"))));
        carddeck.add(new Deck("ks",(new File("Cards/king_of_spades.png"))));
        carddeck.add(new Deck("as",(new File("Cards/ace_of_spades.png"))));
    }
    
    /******************************* Draw Pile **********************************/
    
    public void DrawPile() { // Creates the drawpile.
        Collections.shuffle(carddeck);
        for(Deck shuffledcard : carddeck) {
            drawpile.push(shuffledcard);
        }
    }
    
    @Override
    public String toString() {
        return cardname + " " + cardfile;
    }
}
