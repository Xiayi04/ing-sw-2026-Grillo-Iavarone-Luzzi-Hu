package it.polimi.ingsw.UI.GUI;

import it.polimi.ingsw.Game.Player;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class Playerpanel extends HBox {

    private Label foodLabel;
    private Label PPLabel;

    public Playerpanel(Player player) {
        foodLabel = new Label("Food: "+ player.getFood());
        PPLabel = new Label("PP: "+player.getPrestigePoints());

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/totem/"+player.getTotem().toString().toLowerCase()+"_profilo.png")));
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(70);
        imageView.setOnMouseClicked(event -> {openPlayerHand(player);});
        this.getChildren().add(imageView);

        VBox data = new VBox(5);
        data.getChildren().add(new Label(player.getName()));
        data.getChildren().add(foodLabel);
        data.getChildren().add(PPLabel);
        this.getChildren().add(data);

        this.setSpacing(5);
    }

    public void refresh(Player player) {
        foodLabel.setText("Food: "+ player.getFood());
        PPLabel.setText("PP: "+player.getPrestigePoints());
    }

    public void openPlayerHand(Player player) {
        Stage stage = new Stage();
        Stage currentStage =(Stage) this.getScene().getWindow();
        stage.initOwner(currentStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle(player.getName());

        HBox hand = new  HBox(5);
        hand.setStyle("-fx-background-color: green");
        VBox inventor = new VBox(10);
        VBox builder = new VBox(10);
        VBox painter = new VBox(10);
        VBox hunter = new VBox(10);
        VBox picker = new VBox(10);
        VBox shaman = new VBox(10);
        hand.getChildren().addAll(inventor, builder, painter, hunter, picker, shaman);

        Scene scene = new Scene(hand, 600, 600);
        stage.setScene(scene);
        stage.show();
    }
}
