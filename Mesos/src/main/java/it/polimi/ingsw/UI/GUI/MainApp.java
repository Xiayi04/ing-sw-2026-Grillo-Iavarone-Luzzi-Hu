package it.polimi.ingsw.UI.GUI;

import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.GraphicInterface;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Optional;

public class MainApp extends Application {

    private static ClientController clientController;
    private StackPane root;

    public static void setController(ClientController c) {
        clientController = c;
    }

    /**
     * The method creates the window that will contain all the screens of the game. Clicking on the X of the window
     * launches the method for disconnection.
     * @param mainStage
     */
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
        mainStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo/logo_cranio.png"))));
        mainStage.show();
        view.showLobbyMenu();
    }

    public static void main(String[] args) {
        launch();
    }
}