
package project_3;

import java.io.File;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Project_3 extends Application{

    private String cardname = "";
    private File file = new File("");
    private Deck gamedeck = new Deck(cardname, file);
    private GameLogic gamelogic = new GameLogic(gamedeck); 
    private Player player = new Player(gamedeck, gamelogic);
    private Computer computer = new Computer(gamedeck, gamelogic);
    private boolean playerturn = true;
    private boolean gameover = false;
    
    // GUI Elements
    private Image blank = new Image(getClass().getResource("Cards/card_blank.png").toExternalForm(), 400, 200, true, true);
    private Image back = new Image(getClass().getResource("Cards/card_back.jpg").toExternalForm(), 400, 200, true, true);
    private Image background = new Image(getClass().getResourceAsStream("Cards/BG.jpg"));
    private Button discardpilebutton = new Button();
    private Button playertempbutton = new Button();
    Button computerbutton1 = new Button();
    Button computerbutton2 = new Button();
    Button computerbutton3 = new Button();
    Button computerbutton4 = new Button();
    String buttoncolor = "-fx-base: #6E6E6E;";
    
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage cardstage) throws Exception {
        BorderPane gameboard = new BorderPane();
        gameboard.setTop(ComputerDeck());
        gameboard.setCenter(PileDeck());
        gameboard.setBottom(PlayerDeck());
        
        Alert gamealert = new Alert(AlertType.INFORMATION);
        gamealert.setHeaderText(null);
        gamealert.setTitle("Card Game");
        
        Task<Void> aitask;
        aitask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                return null;
            }

            @Override
            public void run() {
                while (!gameover) {
                    if (gamedeck.getDrawPile().isEmpty()) {
                        gamelogic.ReShuffle();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                discardpilebutton.setGraphic(new ImageView(blank));
                                gamealert.setContentText("The Draw pile has been re-shuffled.");
                                gamealert.showAndWait();
                            }
                        });
                    }
                    if (!playerturn) {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (Exception e) {
                            System.err.println("Cannot sleep at this moment!");
                        }
                        computer.ComputerAI();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Image discardcard = new Image(getClass().getResource(gamedeck.getDiscardPile().peek().getCardFile().toString()).toExternalForm(), 400, 200, true, true);
                                discardpilebutton.setGraphic(new ImageView(discardcard));
                            }
                        });
                        playerturn = true;
                    }
                    if (gamelogic.hasWon(computer.getComputerCards())) {
                        gameover = true;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                ShowComputerCards();
                                gamealert.setContentText("Sorry, You have lost the game.");
                                gamealert.showAndWait();
                                cardstage.close();
                            }
                        });
                    } else if (gamelogic.hasWon(player.getPlayerCards())) {
                        gameover = true;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                ShowComputerCards();
                                gamealert.setContentText("Congratulations, you have won the game!");
                                gamealert.showAndWait();
                                cardstage.close();
                            }
                        });
                    }
                    if (player.getTempCard().isEmpty()) {
                        playertempbutton.setVisible(false);
                    } else {
                        playertempbutton.setVisible(true);
                    }
                }
            }
        };
        
        Thread aithread = new Thread(aitask);
        aithread.setDaemon(true);
        aithread.start();
        
        BackgroundImage cardbackground = new BackgroundImage(background,
        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
          BackgroundSize.DEFAULT);
        gameboard.setBackground(new Background(cardbackground));
        
        Scene cardscene = new Scene(gameboard, 850, 650);
        cardstage.setScene(cardscene);
        cardstage.setTitle("Card Game");
        
        cardstage.show();
    }
    
    public HBox ComputerDeck() {
        HBox computerdeck = new HBox();
        computerdeck.setPadding(new Insets(0,0,0,75));
        
        Alert computeralert = new Alert(AlertType.INFORMATION);
        computeralert.setHeaderText(null);
        computeralert.setTitle("Computer Response");
        
        computerbutton1.setGraphic(new ImageView(back));
        computerbutton1.setStyle(buttoncolor);
        computerbutton1.setOnAction(computerevent -> {
            computeralert.setContentText(computer.AiResponse());
            computeralert.showAndWait();
        });
        
        computerbutton2.setGraphic(new ImageView(back));
        computerbutton2.setStyle(buttoncolor);
        computerbutton2.setOnAction(computerevent -> {
            computeralert.setContentText(computer.AiResponse());
            computeralert.showAndWait();
        });
        
        computerbutton3.setGraphic(new ImageView(back));
        computerbutton3.setStyle(buttoncolor);
        computerbutton3.setOnAction(computerevent -> {
            computeralert.setContentText(computer.AiResponse());
            computeralert.showAndWait();
        });
        
        computerbutton4.setGraphic(new ImageView(back));
        computerbutton4.setStyle(buttoncolor);
        computerbutton4.setOnAction(computerevent -> {
            computeralert.setContentText(computer.AiResponse());
            computeralert.showAndWait();
        });
        computerdeck.getChildren().addAll(computerbutton1, computerbutton2, computerbutton3, computerbutton4);
        
        return computerdeck;
    }
    
    public HBox PileDeck() {
        HBox piledeck = new HBox();
        piledeck.setPadding(new Insets(10,0,0,250));
        
        Alert pilealert = new Alert(AlertType.INFORMATION);
        pilealert.setHeaderText(null);
        pilealert.setTitle("Card Game");
        
        /*********************************** Draw Pile button Action *********************************/
        Button drawpilebutton = new Button();
        drawpilebutton.setStyle(buttoncolor);
        drawpilebutton.setGraphic(new ImageView(back));
        drawpilebutton.setOnAction(drawevent -> {
            if(player.getTempCard().isEmpty() && playerturn) {
                player.PlayerDrawPile();
                Image tempcard = new Image(getClass().getResource(player.getTempCard().peek().getCardFile().toString()).toExternalForm(), 400, 200, true, true);
                playertempbutton.setGraphic(new ImageView(tempcard));
            } else if(!player.getTempCard().isEmpty() && playerturn){
                pilealert.setContentText("Card Already drawn from the deck.");
                pilealert.showAndWait();
            } else {
                pilealert.setContentText("Your turn is over, wait for the computer to play it's turn.");
                pilealert.showAndWait();
            }
        });
        
        /*********************************** Discard Pile button Action *********************************/
        discardpilebutton.setStyle(buttoncolor);
        if(gamedeck.getDiscardPile().isEmpty()) {
            discardpilebutton.setGraphic(new ImageView(blank));
        }
        discardpilebutton.setOnAction(discardevent -> {
            if(player.getTempCard().isEmpty() && !gamedeck.getDiscardPile().isEmpty() && playerturn) {
                Image tempcard = new Image(getClass().getResource(gamedeck.getDiscardPile().peek().getCardFile().toString()).toExternalForm(), 400, 200, true, true);
                playertempbutton.setGraphic(new ImageView(tempcard));
                player.PlayerDiscardPile();
                if(!gamedeck.getDiscardPile().isEmpty()) {
                Image nextcard = new Image(getClass().getResource(gamedeck.getDiscardPile().peek().getCardFile().toString()).toExternalForm(), 400, 200, true, true);
                discardpilebutton.setGraphic(new ImageView(nextcard));
                } else {
                    discardpilebutton.setGraphic(new ImageView(blank));
                }
            } else if(gamedeck.getDiscardPile().isEmpty() && player.getTempCard().isEmpty() && playerturn) {
                pilealert.setContentText("The discard pile is empty.");
                pilealert.showAndWait();
            } else if(!player.getTempCard().isEmpty() && playerturn){
                pilealert.setContentText("Card Already drawn from the deck.");
                pilealert.showAndWait();
            } else {
                pilealert.setContentText("Your turn is over, wait for the computer to play it's turn.");
                pilealert.showAndWait();
            }
        });
        
        piledeck.getChildren().addAll(drawpilebutton, discardpilebutton);
        
        return piledeck;
    }
    
    public HBox PlayerDeck() {
        HBox playerdeck = new HBox();
        playerdeck.setPadding(new Insets(0,0,0,85));
        
        Alert playeralert = new Alert(AlertType.INFORMATION);
        playeralert.setHeaderText(null);
        playeralert.setTitle("Card Game");
        
        Button playerbutton1 = new Button();
        playerbutton1.setStyle(buttoncolor);
        Image playercard1 = new Image(getClass().getResource(player.getPlayerCards().get(0).getCardFile().toString()).toExternalForm(), 400, 200, true, true);
        playerbutton1.setGraphic(new ImageView(playercard1));
        playerbutton1.setOnAction(playerbuttonevent -> {
            if(!player.getTempCard().isEmpty()) {
                Image discardcard = new Image(getClass().getResource(player.getPlayerCards().get(0).getCardFile().toString()).toExternalForm(), 400, 200, true, true);
                Image card1 = new Image(getClass().getResource(player.getTempCard().peek().getCardFile().toString()).toExternalForm(), 400, 200, true, true);
                player.PlayerDiscardCard("playerdeck", 0);
                playerbutton1.setGraphic(new ImageView(card1));
                discardpilebutton.setGraphic(new ImageView(discardcard));
                playertempbutton.setGraphic(new ImageView(blank));
                playerturn = false;
            } else {
                playeralert.setContentText("Pick a card from either the card pile or the discard pile.");
                playeralert.showAndWait();
            }
        });
        
        Button playerbutton2 = new Button();
        playerbutton2.setStyle(buttoncolor);
        Image playercard2 = new Image(getClass().getResource(player.getPlayerCards().get(1).getCardFile().toString()).toExternalForm(), 400, 200, true, true);
        playerbutton2.setGraphic(new ImageView(playercard2));
        playerbutton2.setOnAction(playerbuttonevent -> {
            if(!player.getTempCard().isEmpty()) {
                Image discardcard = new Image(getClass().getResource(player.getPlayerCards().get(1).getCardFile().toString()).toExternalForm(), 400, 200, true, true);
                Image card2 = new Image(getClass().getResource(player.getTempCard().peek().getCardFile().toString()).toExternalForm(), 400, 200, true, true);
                player.PlayerDiscardCard("playerdeck", 1);
                playerbutton2.setGraphic(new ImageView(card2));
                discardpilebutton.setGraphic(new ImageView(discardcard));
                playertempbutton.setGraphic(new ImageView(blank));
                playerturn = false;
            } else {
                playeralert.setContentText("Pick a card from either the card pile or the discard pile.");
                playeralert.showAndWait();
            }
        });
        
        Button playerbutton3 = new Button();
        playerbutton3.setStyle(buttoncolor);
        Image playercard3 = new Image(getClass().getResource(player.getPlayerCards().get(2).getCardFile().toString()).toExternalForm(), 400, 200, true, true);
        playerbutton3.setGraphic(new ImageView(playercard3));
        playerbutton3.setOnAction(playerbuttonevent -> {
            if(!player.getTempCard().isEmpty()) {
                Image discardcard = new Image(getClass().getResource(player.getPlayerCards().get(2).getCardFile().toString()).toExternalForm(), 400, 200, true, true);
                Image card3 = new Image(getClass().getResource(player.getTempCard().peek().getCardFile().toString()).toExternalForm(), 400, 200, true, true);
                player.PlayerDiscardCard("playerdeck", 2);
                playerbutton3.setGraphic(new ImageView(card3));
                discardpilebutton.setGraphic(new ImageView(discardcard));
                playertempbutton.setGraphic(new ImageView(blank));
                playerturn = false;
            } else {
                playeralert.setContentText("Pick a card from either the card pile or the discard pile.");
                playeralert.showAndWait();
            }
        });
        
        Button playerbutton4 = new Button();
        playerbutton4.setStyle(buttoncolor);
        Image playercard4 = new Image(getClass().getResource(player.getPlayerCards().get(3).getCardFile().toString()).toExternalForm(), 400, 200, true, true);
        playerbutton4.setGraphic(new ImageView(playercard4));
        playerbutton4.setOnAction(playerbuttonevent -> {
            if(!player.getTempCard().isEmpty()) {
                Image discardcard = new Image(getClass().getResource(player.getPlayerCards().get(3).getCardFile().toString()).toExternalForm(), 400, 200, true, true);
                Image card4 = new Image(getClass().getResource(player.getTempCard().peek().getCardFile().toString()).toExternalForm(), 400, 200, true, true);
                player.PlayerDiscardCard("playerdeck", 3);
                playerbutton4.setGraphic(new ImageView(card4));
                discardpilebutton.setGraphic(new ImageView(discardcard));
                playertempbutton.setGraphic(new ImageView(blank));
                playerturn = false;
            } else {
                playeralert.setContentText("Pick a card from either the card pile or the discard pile.");
                playeralert.showAndWait();
            }
        });
        
        playertempbutton.setStyle(buttoncolor);
        playertempbutton.setOnAction(playerbuttonevent -> {
            if(!player.getTempCard().isEmpty()){
                Image discardcard = new Image(getClass().getResource(player.getTempCard().peek().getCardFile().toString()).toExternalForm(), 400, 200, true, true);                
                player.PlayerDiscardCard("playertempdeck", -1);
                playertempbutton.setGraphic(new ImageView(blank));
                discardpilebutton.setGraphic(new ImageView(discardcard));
                playerturn = false;
                ShowComputerCards();
                if (gamedeck.getDrawPile().isEmpty() && player.getTempCard().isEmpty()) {
                    gamelogic.ReShuffle();
                            discardpilebutton.setGraphic(new ImageView(blank));
                            playeralert.setContentText("The Draw pile has been re-shuffled.");
                            playeralert.showAndWait();
                }
            }
        });
        
        playerdeck.getChildren().addAll(playerbutton1, playerbutton2, playerbutton3, playerbutton4, playertempbutton);
        
        return playerdeck;
    }
    
    public void ShowComputerCards() { //Method to reveal all the cards of the computer deck when the game has ended.
        Image card1 = new Image(getClass().getResource(computer.getComputerCards().get(0).getCardFile().toString()).toExternalForm(), 400, 200, true, true);
        computerbutton1.setGraphic(new ImageView(card1));
        
        Image card2 = new Image(getClass().getResource(computer.getComputerCards().get(1).getCardFile().toString()).toExternalForm(), 400, 200, true, true);
        computerbutton2.setGraphic(new ImageView(card2));
        
        Image card3 = new Image(getClass().getResource(computer.getComputerCards().get(2).getCardFile().toString()).toExternalForm(), 400, 200, true, true);
        computerbutton3.setGraphic(new ImageView(card3));
        
        Image card4 = new Image(getClass().getResource(computer.getComputerCards().get(3).getCardFile().toString()).toExternalForm(), 400, 200, true, true);
        computerbutton4.setGraphic(new ImageView(card4));
    }
    
}
