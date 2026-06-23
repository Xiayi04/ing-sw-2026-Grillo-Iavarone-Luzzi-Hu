package it.polimi.ingsw.UI.GUI;

import it.polimi.ingsw.Model.Cards.Card;
import it.polimi.ingsw.Model.Game.OfferCard;
import it.polimi.ingsw.Network.ClientController;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * the class manages the display of the board, which includes the path, the cards above and under it, and the pawns.
 * it extends the VBox class in this way the elements that make up the board are inserted in order
 */

public class BoardView extends VBox {
    private final ClientController clientController;
    private final GuiView guiView;
    private final HBox upperRow = new  HBox(10);
    private final HBox centerRow = new  HBox(20);
    private final HBox downRow =  new  HBox(10);
    private final ArrayList<StackPane> TOCSlots = new ArrayList<>();
    private final VBox totems = new VBox(-21);
    private final ArrayList<StackPane> OCSlots = new ArrayList<>();
    private final ExecutorService pool;
    private final Map<Integer, Integer> YCoordinates = Map.of(
            2, 45,
            3, 52,
            4, 57,
            5, 60
    );
    private final Label label = new Label();

    public BoardView(ClientController clientController, GuiView guiView, ExecutorService pool) {
        this.clientController  = clientController;
        this.guiView = guiView;
        this.pool = pool;
    }

    public ArrayList<StackPane> getTOCSlots() {
        return TOCSlots;
    }

    public ArrayList<StackPane> getOCSlots() {
        return OCSlots;
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

    public HBox getDeck(){
        return (HBox) centerRow.getChildren().get(1);
    }

    /**
     * calls the methods showUpperRow, showPath, and showDownRow and inserts the returned elements into the vbox
     */
    public void showBoard() {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().add(showUpperRow());
        this.getChildren().add(showPath());
        this.getChildren().add(showDownRow());
    }

    /**
     * The method creates two HBoxes (one for building cards and the other for character/event cards) and, retrieving
     * data from the local board in the ClientController, displays the cards present in the top row using the
     * createImageView method. For each card, it sets the following features: when clicked, it calls the
     * requestLocalPickCard method; when hovered over with the mouse, the cursor becomes a hand and the card is enlarged.
     * @return a single hbox where the two previously created hboxes are inserted
     */

    public Node showUpperRow(){
        HBox characterRow = new HBox(10);
        HBox buildingRow = new HBox(10);
        upperRow.getChildren().addAll(characterRow, buildingRow);
        upperRow.setAlignment(Pos.CENTER);
        for(Card card : clientController.getCurrentBoard().getUpperCardRow()){
            ImageView imageView = Utils.createImageView(card.getImagePath(), 135);
            imageView.setCursor(Cursor.HAND);
            imageView.setOnMouseClicked(event ->
                {
                    pool.submit(() -> {
                        clientController.requestLocalPickCard(true, false, getUpperCharacterRow().getChildren().indexOf(imageView));
                    });
                });
            imageView.setOnMouseEntered(event -> {
                imageView.setStyle(" -fx-scale-x: 1.2; -fx-scale-y: 1.2;");
            });
            imageView.setOnMouseExited(event -> {
                imageView.setStyle("");
            });
            characterRow.getChildren().add(imageView);
        }
        for(Card b : clientController.getCurrentBoard().getBuildingsEra1()) {
            ImageView imageView = Utils.createImageView(b.getImagePath(), 135);
            imageView.setCursor(Cursor.HAND);
            imageView.setOnMouseClicked(event ->
                {
                    pool.submit(() -> {
                        clientController.requestLocalPickCard(true, true, getUpperBuildingRow().getChildren().indexOf(imageView));
                    });
                });
            imageView.setOnMouseEntered(event -> {
                imageView.setStyle(" -fx-scale-x: 1.2; -fx-scale-y: 1.2;");
            });
            imageView.setOnMouseExited(event -> {
                imageView.setStyle("");
            });
            buildingRow.getChildren().add(imageView);
        }
        return upperRow;
    }

    /**
     * The method creates two HBoxes (one for building cards and the other for character/event cards) and, retrieving
     * data from the local board in the ClientController, displays the cards present in the bottom row using the
     * createImageView method. For each card, it sets the following features: when clicked, it calls the
     * requestLocalPickCard method; when hovered over with the mouse, the cursor becomes a hand and the card is enlarged.
     * @return a single hbox where the two previously created hboxes are inserted
     */

    public Node showDownRow(){
        HBox characterRow = new HBox(10);
        HBox buildingRow = new HBox(10);
        downRow.getChildren().addAll(characterRow, buildingRow);
        downRow.setAlignment(Pos.CENTER);
        for(Card card : clientController.getCurrentBoard().getLowerCardsRow()){
            ImageView imageView = Utils.createImageView(card.getImagePath(), 135);
            imageView.setCursor(Cursor.HAND);
            imageView.setOnMouseClicked(event ->
                {
                    pool.submit(() -> {
                        clientController.requestLocalPickCard(false, false, getDownCharacterRow().getChildren().indexOf(imageView));
                    });
                });
            imageView.setOnMouseEntered(event -> {
                imageView.setStyle(" -fx-scale-x: 1.2; -fx-scale-y: 1.2;");
            });
            imageView.setOnMouseExited(event -> {
                imageView.setStyle("");
            });
            characterRow.getChildren().add(imageView);
        }
        return downRow;
    }

    /**
     * The method displays the path and the totems above it. It inserts the turn order card into a stackpane so that
     * images of the totems can be displayed on top, which are inserted into other stackpanes (one per player) and
     * superimposed on the image of the turn order card.
     * It displays the path next to it: the tiles are clickable and when clicked they call the method
     * requestLocalMoveTotem, and additionally, when hovered over with the mouse, a yellow border appears to facilitate
     * selection.
     * The deck and a "skip" button are also shown, which when pressed calls the requestSkip method.
     * Inserts all these elements of the hbox centerRow
     * @return centerRow
     */

    public Node showPath(){
        HBox path =  new HBox(-1);
        HBox deck = new HBox();
        centerRow.getChildren().addAll(path,deck);
        centerRow.setAlignment(Pos.CENTER);
        //turn order card + totems
        ImageView orderCardView = Utils.createImageView("/images/orderCard/"+clientController.getCurrentBoard().getPlayers().size()+"giocatori.png", 150);
        StackPane orderCardContainer = new StackPane();
        orderCardContainer.getChildren().add(orderCardView);
        totems.setManaged(false);
        totems.setAlignment(Pos.CENTER);
        for(int i=0; i<clientController.getCurrentBoard().getPlayers().size(); i++){
            StackPane TOCslot = new StackPane();
            TOCslot.setMinSize(70, 50);
            TOCSlots.add(TOCslot);
            totems.getChildren().add(TOCslot);
        }
        orderCardContainer.getChildren().add(totems);
        totems.setTranslateX(47);
        totems.setTranslateY(YCoordinates.get(clientController.getCurrentBoard().getPlayers().size()));
        //riempio gli stackpane con i totem
        for(int i=0; i<clientController.getCurrentBoard().getPlayers().size(); i++){
            String totem = clientController.getCurrentBoard().getTurnOrderCard().getOrder().get(i).getTotem().toString();
            ImageView totemView = Utils.createImageView("/images/totem/"+totem.toLowerCase()+"_profilo.png", 40);
            TOCSlots.get(i).getChildren().add(totemView);
        }
        path.getChildren().add(orderCardContainer);
        for(int i=0; i<clientController.getCurrentBoard().getPath().size(); i++){
            final int index = i;
            OfferCard c = clientController.getCurrentBoard().getPath().get(i);
            ImageView offerCardView = Utils.createImageView("/images/tessereOfferta/"+c.getID()+".png", 150);
            StackPane offerCardContainer = new StackPane();
            offerCardContainer.getChildren().add(offerCardView);
            StackPane OCSlot = new StackPane();
            //ImageView totem = Utils.createImageView("images/totem/black_profilo.png", 40);
            //OCSlot.getChildren().add(totem);
            OCSlot.setMinSize(70, 50);
            OCSlot.setTranslateY(-48);
            OCSlots.add(OCSlot);
            offerCardContainer.getChildren().add(OCSlot);
            offerCardContainer.setCursor(Cursor.HAND);
            //bordo giallo
            Rectangle border = new Rectangle(90, offerCardView.getFitHeight());
            border.setFill(Color.TRANSPARENT);
            border.setStroke(Color.YELLOW);
            border.setStrokeWidth(3);
            border.setVisible(false);
            border.setMouseTransparent(true);
            offerCardContainer.getChildren().add(border);
            offerCardContainer.setOnMouseEntered(event -> {
                border.setVisible(true);
            });
            offerCardContainer.setOnMouseExited(event -> {
                border.setVisible(false);
            });
            offerCardContainer.setOnMouseClicked(event -> {
                pool.submit(() -> {
                    //System.out.println("DEBUG: richiesta spostare totem");
                    clientController.requestLocalMoveTotem(index);
                });
            });
            path.getChildren().add(offerCardContainer);
        }
        Card c = clientController.getCurrentBoard().getDeck().getFirst();
        ImageView imageView = Utils.createImageView("/images/cards/back/era"+c.getEra()+".png", 135);
        deck.getChildren().add(imageView);
        deck.getChildren().add(label);
        Button skip = new Button("SKIP");
        skip.setOnAction(event -> {
            pool.submit(() -> {
                clientController.requestSkip();
            });
        });
        centerRow.getChildren().add(skip);
        return centerRow;
    }
}
