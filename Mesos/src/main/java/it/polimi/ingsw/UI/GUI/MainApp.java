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
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class MainApp extends Application {

    private static ClientController clientController;
    private BorderPane root;

    public static void setController(ClientController c) {
        clientController = c;
    }

    @Override
    public void start(Stage mainStage) {
        root = new BorderPane();
        GraphicInterface view =  new GuiView(clientController, root);
        clientController.setView(view);

        Scene scene = new Scene(root, 300, 200);

        mainStage.setScene(scene);
        mainStage.setOnCloseRequest(event -> {
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
        });
        mainStage.setTitle("Mesos");
        mainStage.setMaximized(true);
        mainStage.getIcons().add(new Image(new File("images/logo/logo_cranio.png").toURI().toString()));
        mainStage.show();
        view.showLobbyMenu();
    }

    public static void main(String[] args) {
        launch();
    }
}