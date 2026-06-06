package it.polimi.ingsw.UI.GUI;

import it.polimi.ingsw.Buildings.SameIconBuilding;
import it.polimi.ingsw.Cards.Characters.*;
import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.OfferCard;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.GraphicInterface;
import it.polimi.ingsw.Network.PlayerScore;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MainApp extends Application {

    private static ClientController clientController;
    private StackPane root;

    public static void setController(ClientController c) {
        clientController = c;
    }

    @Override
    public void start(Stage mainStage) {
        root = new StackPane();
        GraphicInterface view =  new GuiView(clientController, root);
        clientController.setView(view);

        Scene scene = new Scene(root, 300, 200);

        mainStage.setScene(scene);
        mainStage.setOnCloseRequest(event -> {
            if(clientController.getConnection() != null){
                ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.YES);
                ButtonType no = new ButtonType("no", ButtonBar.ButtonData.NO);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to log out?", yes, no);
                alert.setGraphic(null);
                alert.setHeaderText(null);
                alert.setTitle("Disconnection");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == yes){
                    clientController.leave();
                }
                else{
                    event.consume();
                }
            }
            else{
                mainStage.close();
            }

        });
        mainStage.setTitle("Mesos");
        mainStage.setMaximized(true);
        mainStage.getIcons().add(new Image(new File("images/logo/logo_cranio.png").toURI().toString()));
        mainStage.show();
        /*List<PlayerScore> leaderboard = new ArrayList<>();
        leaderboard.add(new PlayerScore("a", 39, 4));
        leaderboard.add(new PlayerScore("b", 54, 3));
        leaderboard.add(new PlayerScore("c", 67, 6));
        leaderboard.add(new PlayerScore("d", 23, 5));
        leaderboard.add(new PlayerScore("e", 39, 6));
        // Raking order ( criterion: points and then food)
        leaderboard.sort((p1, p2) -> {
            int pointsCompare = Integer.compare(p2.points(), p1.points());
            if (pointsCompare != 0){
                return pointsCompare;
            }else {
                return Integer.compare(p2.food(), p1.food());
            }
        });
        String winner = leaderboard.get(0).username();*/
        view.showLobbyMenu();
    }

    public static void main(String[] args) {
        launch();
    }
}