package it.polimi.ingsw.UI.GUI;


import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Game.OfferCard;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Network.ClientController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GuiView {
    private final ClientController clientController;
    private BoardView boardView;
    private Map<String, Playerpanel> panels = new HashMap<>();

    public GuiView(ClientController clientController) {
        this.clientController = clientController;
    }

    public BorderPane createDivision() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: red");
        root.setTop(showPlayersBar());
        boardView = new BoardView(clientController.getCurrentBoard());
        root.setCenter(boardView);
        root.setBottom(showLocalPlayer());
        return root;
    }

    public Node showPlayersBar(){
        HBox playersBar = new HBox(50);
        playersBar.setAlignment(Pos.CENTER);
        playersBar.setPadding(new Insets(20));
        for(Player p : clientController.getCurrentBoard().getPlayers()){
            if (!p.getName().equals(clientController.getLocalPlayer().getName())){
                panels.put(p.getName(), new Playerpanel(p, this));
                playersBar.getChildren().add(panels.get(p.getName()));
            }
        }
        return playersBar;
    }

    public Node showLocalPlayer(){
        for(Player p : clientController.getCurrentBoard().getPlayers()){
            if(p.getName().equals(clientController.getLocalPlayer().getName())){
                Playerpanel localPlayer = new Playerpanel(p, this);
                localPlayer.setAlignment(Pos.CENTER);
                localPlayer.setPadding(new Insets(0, 0, 20, 0));
                return localPlayer;
            }
        }
        return null;
    }

    /*public void showHand(Player player){
        Stage stage = new Stage();
        Stage currentStage =(Stage) boardView.getScene().getWindow();
        stage.initOwner(currentStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle(player.getName());

        PlayerHandView playerHandView = new PlayerHandView(player);

        Scene scene = new Scene(playerHandView, 600, 600);
        stage.setScene(scene);
        stage.show();
    }*/

    public void pickCard(String username, boolean row, boolean isBuilding, int index){
        if(index<0)
            throw new IllegalArgumentException("Invalid index");
        if(!row){
            if(isBuilding){
                boardView.getUpperBuildingRow().getChildren().remove(index);
            }
            else{
                boardView.getUpperCharacterRow().getChildren().remove(index);
            }
        }
        else{
            if(isBuilding){
                boardView.getDownBuildingRow().getChildren().remove(index);
            }
            else{
                boardView.getDownCharacterRow().getChildren().remove(index);
            }
        }
    }
}
