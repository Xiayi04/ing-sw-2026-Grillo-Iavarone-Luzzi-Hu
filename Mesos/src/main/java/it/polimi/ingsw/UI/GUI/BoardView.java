package it.polimi.ingsw.UI.GUI;

import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.OfferCard;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class BoardView extends VBox {
    private final Board currentBoard;
    private HBox upperRow;
    private HBox downRow;

    public BoardView(Board currentBoard) {
        this.currentBoard = currentBoard;
        showBoard();
    }

    public HBox getUpperCharacterRow() {
        return (HBox) upperRow.getChildren().getFirst();
    }

    public HBox getDownCharacterRow() {
        return (HBox) downRow.getChildren().getFirst();
    }

    public HBox getUpperBuildingRow() {
        return (HBox) upperRow.getChildren().get(1);
    }

    public HBox getDownBuildingRow() {
        return (HBox) downRow.getChildren().get(1);
    }

    public void showBoard() {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
        this.getChildren().add(showUpperRow());
        this.getChildren().add(showPath());
        this.getChildren().add(showDownRow());
    }

    public Node showUpperRow(){
        upperRow = new HBox(10);
        HBox characterRow = new HBox(10);
        HBox buildingRow = new HBox(10);
        upperRow.getChildren().addAll(characterRow, buildingRow);
        upperRow.setAlignment(Pos.CENTER);
        for(Card card : currentBoard.getUpperCardRow()){
            /*Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(card.getImagePath())));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(150);*/
            ImageView imageView = Utils.createImageView(card.getImagePath(), 150);
            characterRow.getChildren().add(imageView);
        }
        for(Card b : currentBoard.getBuildingsEra1()) {
            /*Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(b.getImagePath())));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(150);*/
            ImageView imageView = Utils.createImageView(b.getImagePath(), 150);
            buildingRow.getChildren().add(imageView);
        }
        return upperRow;
    }

    public Node showDownRow(){
        downRow = new HBox(10);
        HBox characterRow = new HBox(10);
        HBox buildingRow = new HBox(10);
        downRow.getChildren().addAll(characterRow, buildingRow);
        downRow.setAlignment(Pos.CENTER);
        for(Card card : currentBoard.getLowerCardsRow()){
            /*Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(card.getImagePath())));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(150);*/
            ImageView imageView = Utils.createImageView(card.getImagePath(), 150);
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
        /*Image orderCard = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/orderCard/"+currentBoard.getPlayers().size()+"giocatori.png")));
        ImageView orderCardView = new ImageView(orderCard);
        orderCardView.setPreserveRatio(true);
        orderCardView.setFitHeight(150);*/
        ImageView orderCardView = Utils.createImageView("images/orderCard/"+currentBoard.getPlayers().size()+"giocatori.png", 150);
        path.getChildren().add(orderCardView);
        for(OfferCard c : currentBoard.getPath()){
            /*Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/tessereOfferta/"+c.getID()+".png")));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(150);*/
            ImageView imageView = Utils.createImageView("images/tessereOfferta/"+c.getID()+".png", 150);
            path.getChildren().add(imageView);
        }
        Card c = currentBoard.getDeck().getFirst();
        /*Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cards/back/era"+c.getEra()+".png")));
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(150);*/
        ImageView imageView = Utils.createImageView("images/cards/back/era"+c.getEra()+".png", 150);
        deck.getChildren().add(imageView);
        return centerRow;
    }
}
