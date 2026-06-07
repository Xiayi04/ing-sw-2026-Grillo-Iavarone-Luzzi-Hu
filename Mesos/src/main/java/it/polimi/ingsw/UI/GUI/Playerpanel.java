package it.polimi.ingsw.UI.GUI;

import it.polimi.ingsw.Game.Player;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class Playerpanel extends HBox {

    private Label foodLabel;
    private Label PPLabel;
    private GuiView guiView;
    private PlayerHandView playerHandView;
    //private Stage handStage;
    private Player player;

    public Playerpanel(Player p, GuiView guiView) {
        this.guiView = guiView;
        this.player = p;
        foodLabel = new Label("Food: "+ player.getFood());
        PPLabel = new Label("PP: "+player.getPrestigePoints());

        ImageView imageView = Utils.createImageView("images/totem/"+player.getTotem().toString().toLowerCase()+"_profilo.png", 70);
        imageView.setCursor(Cursor.HAND);
        imageView.setOnMouseClicked(event -> {
            openPlayerHand(player);
        });  //metto al posto di openPlayerHand(player) showHand()
        this.getChildren().add(imageView);

        VBox data = new VBox(5);
        data.getChildren().add(new Label(player.getName()));
        data.getChildren().add(foodLabel);
        data.getChildren().add(PPLabel);
        this.getChildren().add(data);

        this.setSpacing(5);
    }

    public void setPlayer(Player p) {
        this.player = p;
    }

    public PlayerHandView getPlayerHandView() {
        return playerHandView;
    }

    public void refreshFood(Player player) {
        foodLabel.setText("Food: "+ player.getFood());
    }

    public void refreshPP(Player player) {
        PPLabel.setText("PP: "+player.getPrestigePoints());
    }

    public void openPlayerHand(Player player) {
        Stage stage = new Stage();
        Stage currentStage =(Stage) this.getScene().getWindow();
        stage.initOwner(currentStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle(player.getName());

        this.playerHandView = new PlayerHandView(player);
        playerHandView.setAlignment(Pos.CENTER);
        StackPane root = new StackPane(playerHandView);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 670, 620);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    //metodo per creare una sola playerHandView da aggiornare manualmente
    /*public void showHand() {
        if (playerHandView == null) {
            playerHandView = new PlayerHandView(player);
            handStage = new  Stage();
            Stage currentStage = (Stage) getScene().getWindow();

            handStage.initOwner(currentStage);
            handStage.initModality(Modality.WINDOW_MODAL);
            handStage.setTitle(player.getName());

            handStage.setScene(new Scene(playerHandView, 600, 600));
        }
        handStage.show();
    }*/
}
