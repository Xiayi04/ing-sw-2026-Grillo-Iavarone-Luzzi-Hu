package it.polimi.ingsw.UI.GUI;

import it.polimi.ingsw.Buildings.SameIconBuilding;
import it.polimi.ingsw.Cards.Characters.*;
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
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Painter(1, "character", 2, "PAINTER"));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Painter(1, "character", 2, "PAINTER"));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Painter(1, "character", 2, "PAINTER"));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Painter(1, "character", 2, "PAINTER"));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Painter(1, "character", 2, "PAINTER"));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Hunter(1, "character", 2, "HUNTER", false));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Hunter(1, "character", 2, "HUNTER", false));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Hunter(1, "character", 2, "HUNTER", false));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Hunter(1, "character", 2, "HUNTER", false));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Hunter(1, "character", 2, "HUNTER", false));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Builder(1, "character", 2, "BUILDER", 1, 3));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Builder(1, "character", 2, "BUILDER", 1, 3));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Builder(1, "character", 2, "BUILDER", 1, 3));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Builder(1, "character", 2, "BUILDER", 1, 3));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Builder(1, "character", 2, "BUILDER", 1, 3));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Picker(1, "character", 2, "PICKER"));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Picker(1, "character", 2, "PICKER"));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Picker(1, "character", 2, "PICKER"));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Picker(1, "character", 2, "PICKER"));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Picker(1, "character", 2, "PICKER"));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Inventor(1, "character", 2, "INVENTOR", "TREE"));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Inventor(1, "character", 2, "INVENTOR", "TREE"));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Inventor(1, "character", 2, "INVENTOR", "TREE"));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Inventor(1, "character", 2, "INVENTOR", "TREE"));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Inventor(1, "character", 2, "INVENTOR", "TREE"));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Shaman(1, "character", 2, "SHAMAN", 1));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Shaman(1, "character", 2, "SHAMAN", 1));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Shaman(1, "character", 2, "SHAMAN", 1));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Shaman(1, "character", 2, "SHAMAN", 1));
        controller.getCurrentBoard().getPlayers().get(0).getTribeCard().add(new Shaman(1, "character", 2, "SHAMAN", 1));
        controller.getCurrentBoard().getPlayers().get(0).getBuilding().add(new SameIconBuilding(1, 3, 4));
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