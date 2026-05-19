package it.polimi.ingsw.UI.GUI;

import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.OfferCard;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.ClientController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MainApp extends Application {

    private static ClientController clientController;
    public static void setController(ClientController c) {
        clientController = c;
    }

    @Override
    public void start(Stage mainStage) {
        ClientController controller = new ClientController();
        controller.setCurrentBoard(new Board());
        controller.getCurrentBoard().getPlayers().add(new Player("mattia", Totem.BLACK, 2, null));
        controller.getCurrentBoard().getPlayers().add(new Player("xiayi", Totem.YELLOW, 2, null));
        controller.getCurrentBoard().getPlayers().add(new Player("gloria", Totem.BLUE, 2, null));
        controller.getCurrentBoard().getPlayers().add(new Player("viola", Totem.ORANGE, 2, null));
        controller.getCurrentBoard().getPlayers().add(new Player("giuseppe", Totem.WHITE, 2, null));
        controller.getCurrentBoard().obtainPath(controller.getCurrentBoard().getPlayers());
        controller.getCurrentBoard().initializeBoard(controller.getCurrentBoard().getPlayers().size());
        controller.setLocalPlayerName("xiayi");
        GuiView gui =  new GuiView(controller);
        BorderPane root = gui.createDivision();

        Scene scene = new Scene(root, 300, 200);

        mainStage.setScene(scene);
        mainStage.setTitle("Mesos");
        mainStage.setFullScreen(true);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}