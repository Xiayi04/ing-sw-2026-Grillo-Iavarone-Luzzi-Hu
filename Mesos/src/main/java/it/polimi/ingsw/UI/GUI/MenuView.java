package it.polimi.ingsw.UI.GUI;

import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.ClientController;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;

public class MenuView extends VBox {
    private final ClientController clientController;
    private final GuiView guiView;
    private final VBox connection = new VBox(5);
    private final VBox username =  new VBox(5);
    private final VBox totemsLine = new VBox(10);
    private final HBox login = new HBox();
    private final VBox numPlayers = new VBox(5);
    private final Label label;
    private final ExecutorService pool;
    private Label provaUsername = new Label();
    private Label provaTotem = new Label();

    public MenuView(ClientController clientController, GuiView guiView, Label label, ExecutorService pool) {
        this.clientController = clientController;
        this.guiView = guiView;
        this.label = label;
        this.pool = pool;
    }

    public VBox getNumPlayers() {
        return numPlayers;
    }

    public void createSchema(){
        ArrayList<Totem> totems = new ArrayList<>();
        totems.addAll(Arrays.asList(Totem.values()));
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().add(numPlayers);
        this.getChildren().add(chooseConnection());
        this.getChildren().add(chooseUsername());
        this.getChildren().add(showAvailableTotems(totems));
        this.getChildren().add(label);
        this.getChildren().add(provaTotem);
        this.getChildren().add(provaUsername);
    }

    public VBox chooseConnection(){
        connection.setAlignment(Pos.CENTER);
        Label text  = new Label();
        text.setText("Please choose the preferred connection protocol:");
        connection.getChildren().add(text);
        HBox hBox = new HBox(5);
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("RMI", "Socket");
        Button button = new Button("Confirm");
        button.setOnAction(event -> {
           String connection = comboBox.getValue();
           pool.submit(() -> {
               clientController.setServerConnection(connection.equals("RMI"));
               System.out.println("DEBUG: la connessione è "+connection);
           });
           /*new Thread(() -> {
               clientController.setServerConnection(connection.equals("RMI"));
           }).start();*/
        });
        hBox.getChildren().addAll(comboBox,button);
        hBox.setAlignment(Pos.CENTER);
        connection.getChildren().add(hBox);
        return connection;
    }

    public VBox chooseUsername(){
        username.setAlignment(Pos.CENTER);
        Label text  = new Label();
        text.setText("Please choose a username:");
        username.getChildren().add(text);
        HBox  hBox = new HBox(5);
        hBox.setAlignment(Pos.CENTER);
        Button button = new Button("Confirm");
        TextField space = new TextField();
        button.setOnAction(event -> {
            String name = space.getText();
            pool.submit(() -> {
                clientController.setTmpUsername(name);
                System.out.println("DEBUG: l'username è "+name);
            });
            /*new Thread(()->{
                clientController.setTmpUsername(name);
                System.out.println("DEBUG: l'username è "+name);
            }).start();*/
        });
        hBox.getChildren().addAll(space,button);
        space.setAlignment(Pos.CENTER);
        space.setMaxWidth(150);
        username.getChildren().add(hBox);
        return username;
    }

    public VBox showAvailableTotems(ArrayList<Totem> totems){
        totemsLine.getChildren().clear();
        totemsLine.setAlignment(Pos.CENTER);
        Label text  = new Label();
        text.setText("Please choose your totem:");
        text.setAlignment(Pos.CENTER);
        HBox hBox = new HBox(50);
        for( Totem totem : totems){
            ImageView imageView = Utils.createImageView("images/totem/"+totem.toString().toLowerCase()+"_frontale.png", 70);
            hBox.getChildren().add(imageView);
            imageView.setCursor(Cursor.HAND);
            imageView.setOnMouseClicked(event -> {
                pool.submit(() -> {
                    clientController.setTmpTotem(totem);
                    System.out.println("DEBUG: il totem è "+totem.toString());
                });
                /*new Thread(()->{
                    clientController.setTmpTotem(totem);
                    System.out.println("DEBUG: il totem è "+totem.toString());
                }).start();*/
            });
            imageView.setOnMouseEntered(event -> {
                imageView.setStyle(" -fx-scale-x: 1.2; -fx-scale-y: 1.2;");
            });
            imageView.setOnMouseExited(event -> {
                imageView.setStyle("");
            });
        }
        hBox.setAlignment(Pos.CENTER);
        totemsLine.getChildren().addAll(text,hBox);
        return totemsLine;
    }

}
