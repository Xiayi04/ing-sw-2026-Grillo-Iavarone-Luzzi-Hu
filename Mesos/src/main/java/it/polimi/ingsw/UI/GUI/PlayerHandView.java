package it.polimi.ingsw.UI.GUI;

import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Cards.Characters.*;
import it.polimi.ingsw.Cards.Characters.Character;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.GuiCharacterVisitor;
import it.polimi.ingsw.Cards.Characters.CharacterVisitor.GuiVisitor;
import it.polimi.ingsw.Game.Player;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlayerHandView extends VBox {
    private HBox building;
    private HBox hand;
    private Player player;
    private Map<Class, VBox> boxes = new HashMap<>();

    public PlayerHandView(Player player){
        this.player = player;

        this.setSpacing(20);
        this.setStyle("-fx-background-color: red");
        this.setAlignment(Pos.CENTER);

        building = new HBox(10);

        hand = new HBox(10);
        hand.setAlignment(Pos.CENTER);
        hand.setMaxWidth(Region.USE_PREF_SIZE);

        HBox wrapper = new HBox(building);
        wrapper.setAlignment(Pos.CENTER_LEFT);

        VBox inventor = new VBox(-100);
        VBox builder = new VBox(-100);
        VBox painter = new VBox(-100);
        VBox hunter = new VBox(-100);
        VBox picker = new VBox(-100);
        VBox shaman = new VBox(-100);

        wrapper.minWidthProperty().bind(hand.widthProperty());
        wrapper.prefWidthProperty().bind(hand.widthProperty());
        wrapper.maxWidthProperty().bind(hand.widthProperty());

        hand.getChildren().addAll(inventor, builder, painter, hunter, picker, shaman);
        this.getChildren().addAll(hand, wrapper);

        boxes.put(Inventor.class, inventor);
        boxes.put(Hunter.class, hunter);
        boxes.put(Picker.class, picker);
        boxes.put(Shaman.class, shaman);
        boxes.put(Builder.class, builder);
        boxes.put(Painter.class, painter);
        fillPlayerHand();
    }

    public HBox getHand() {
        return hand;
    }

    public HBox getBuilding() {
        return building;
    }

    public void fillPlayerHand(){
        GuiVisitor visitor = new GuiCharacterVisitor(boxes);
        for(Character c : player.getTribeCard()){
            ImageView imageView = Utils.createImageView(c.getImagePath(), 150);
            imageView.setOnMouseEntered(event -> {
                imageView.setViewOrder(-1);
                imageView.setTranslateX(-10);
                imageView.setTranslateY(-10);
            });
            imageView.setOnMouseExited(event -> {
                imageView.setViewOrder(0);
                imageView.setTranslateX(0);
                imageView.setTranslateY(0);
            });
            c.findBox(visitor).getChildren().add(imageView);
        }
        for(Building b : player.getBuilding()){
            ImageView imageView = Utils.createImageView(b.getImagePath(), 150);
            building.getChildren().add(imageView);
        }
    }
}
