package it.polimi.ingsw.UI.GUI;


import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Game.OfferCard;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Network.ClientController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GuiView {
    private final ClientController clientController;
    private Map<String, Playerpanel> panels = new HashMap<>();

    public GuiView(ClientController clientController) {
        this.clientController = clientController;
    }

    public BorderPane createDivision() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: red");
        root.setTop(showPlayersBar());
        root.setCenter(showBoard());
        root.setBottom(showLocalPlayer());
        return root;
    }

    public Node showBoard() {
        VBox board = new VBox(10);
        board.setAlignment(Pos.CENTER);
        board.getChildren().add(showUpperRow());
        board.getChildren().add(showPath());
        board.getChildren().add(showDownRow());
        return board;
    }

    public Node showUpperRow(){
        HBox upperRow = new HBox(10);
        HBox characterRow = new HBox(10);
        HBox buildingRow = new HBox(10);
        upperRow.getChildren().addAll(characterRow, buildingRow);
        upperRow.setAlignment(Pos.CENTER);
        for(Card card : clientController.getCurrentBoard().getUpperCardRow()){
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(card.getImagePath())));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(150);
            characterRow.getChildren().add(imageView);
        }
        for(Card b : clientController.getCurrentBoard().getBuildingsEra1()) {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(b.getImagePath())));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(150);
            buildingRow.getChildren().add(imageView);
        }
        return upperRow;
    }

    public Node showDownRow(){
        HBox downRow = new HBox(10);
        HBox characterRow = new HBox(10);
        HBox buildingRow = new HBox(10);
        downRow.getChildren().addAll(characterRow, buildingRow);
        downRow.setAlignment(Pos.CENTER);
        for(Card card : clientController.getCurrentBoard().getLowerCardsRow()){
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(card.getImagePath())));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(150);
            characterRow.getChildren().add(imageView);
        }
        return downRow;
    }

    public Node showPath(){
        HBox centerRow = new HBox(20);
        HBox path =  new HBox(-1);
        HBox deck = new HBox();
        centerRow.getChildren().addAll(path,deck);
        centerRow.setAlignment(Pos.CENTER);
        Image orderCard = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/orderCard/"+clientController.getCurrentBoard().getPlayers().size()+"giocatori.png")));
        ImageView orderCardView = new ImageView(orderCard);
        orderCardView.setPreserveRatio(true);
        orderCardView.setFitHeight(150);
        path.getChildren().add(orderCardView);
        for(OfferCard c : clientController.getCurrentBoard().getPath()){
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/tessereOfferta/"+c.getID()+".png")));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(150);
            path.getChildren().add(imageView);
        }
        Card c = clientController.getCurrentBoard().getDeck().getFirst();
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cards/back/era"+c.getEra()+".png")));
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(150);
        deck.getChildren().add(imageView);
        return centerRow;
    }

    public Node showPlayersBar(){
        HBox playersBar = new HBox(50);
        playersBar.setAlignment(Pos.CENTER);
        playersBar.setPadding(new Insets(20));
        for(Player p : clientController.getCurrentBoard().getPlayers()){
            if (!p.getName().equals(clientController.getLocalPlayer().getName())){
                panels.put(p.getName(), new Playerpanel(p));
                playersBar.getChildren().add(panels.get(p.getName()));
            }
        }
        return playersBar;
    }

    public Node showLocalPlayer(){
        for(Player p : clientController.getCurrentBoard().getPlayers()){
            if(p.getName().equals(clientController.getLocalPlayer().getName())){
                Playerpanel localPlayer = new Playerpanel(p);
                localPlayer.setAlignment(Pos.CENTER);
                localPlayer.setPadding(new Insets(0, 0, 20, 0));
                return localPlayer;
            }
        }
        return null;
    }
}
