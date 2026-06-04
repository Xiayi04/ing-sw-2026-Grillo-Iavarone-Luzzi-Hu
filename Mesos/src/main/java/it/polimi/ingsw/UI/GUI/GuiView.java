package it.polimi.ingsw.UI.GUI;


import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Database.LeaderBoardData;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.GraphicInterface;
import it.polimi.ingsw.Network.PlayerScore;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuiView implements GraphicInterface {
    private final ClientController clientController;
    private final BoardView boardView;
    private final MenuView menuView;
    private final Map<String, Playerpanel> panels = new HashMap<>();
    private final BorderPane root;
    private final Label label = new Label();
    private final ExecutorService pool = Executors.newCachedThreadPool();
    //private final Label labelError = new Label("prova");

    public GuiView(ClientController clientController, BorderPane root) {
        this.clientController = clientController;
        this.menuView = new MenuView(clientController, this, label, pool);
        this.boardView = new BoardView(clientController, this, pool);
        this.root = root;
    }

    public void showMainStage() {
        root.getChildren().clear();
        root.setStyle("-fx-background-color: red");
        root.setTop(showPlayersBar());
        boardView.showBoard();
        root.setCenter(boardView);
        HBox bottomBar = new HBox();
        bottomBar.setPrefWidth(Double.MAX_VALUE);
        Node localPlayer = showLocalPlayer();
        StackPane centerWrapper = new StackPane(localPlayer);
        centerWrapper.setAlignment(Pos.CENTER);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        bottomBar.getChildren().addAll(centerWrapper, spacer, label);
        root.setBottom(bottomBar);
        //root.setBottom(labelError);
    }

    public void showLobbyMenu() {
        root.setStyle("-fx-background-color: red");
        menuView.createSchema();
        root.setCenter(menuView);
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.getChildren().addAll(label/*labelError*/);
        root.setBottom(hBox);
    }

    public void showErrorMessage(String message) {

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

    public void pickCard(String username, boolean isUpper, boolean isBuilding, int index){
        Platform.runLater(() -> {
            if(index<0)
                throw new IllegalArgumentException("Invalid index");
            if(isUpper){
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
        });
    }

    public void moveTotem(String username, int index) {
        System.out.println("DEBUG: totem mosso");
        Platform.runLater(() -> {
            //String thisTotem = clientController.getPlayerByName(username).getTotem().toString();
            for(int i=0; i<boardView.getTOCSlots().size(); i++){
                if(!boardView.getTOCSlots().get(i).getChildren().isEmpty()){
                    Node thisTotemView = boardView.getTOCSlots().get(i).getChildren().remove(0);
                    boardView.getOCSlots().get(index).getChildren().add(thisTotemView);
                    break;
                }
            }
        });
    }

    public void showNextRound(){
        Platform.runLater(() -> {
            boardView.getDownCharacterRow().getChildren().clear();
            while(!boardView.getUpperCharacterRow().getChildren().isEmpty()){
                Node card = boardView.getUpperCharacterRow().getChildren().remove(0);
                boardView.getDownCharacterRow().getChildren().add(card);
            }
            boardView.showUpperRow();
            boardView.getDeck().getChildren().clear();
            Card c = clientController.getCurrentBoard().getDeck().getFirst();
            ImageView imageView = Utils.createImageView("images/cards/back/era"+c.getEra()+".png", 150);
            boardView.getDeck().getChildren().add(imageView);
        });
    }

    public void showStartGame() {
        Platform.runLater(() -> {
            this.showMainStage();
        });
    }

    @Override
    public void showLeaderboardFromDB(int playerPosition, List<LeaderBoardData> updatedDB) {

    }

    @Override
    public void showEndGameSuccessfully(String winner, List<PlayerScore> leaderboard) {

    }

    @Override
    public void close() {

    }

    /*public void askNumToPlayer() {
        menuView.getNumPlayers().setAlignment(Pos.CENTER);
        Label text = new Label();
        text.setAlignment(Pos.CENTER);
        text.setText("You are the first player, choose the number of player for this game:");
        HBox hbox = new HBox(5);
        hbox.setAlignment(Pos.CENTER);
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("2", "3", "4", "5");
        Button button = new Button("confirm");
        button.setOnAction(event -> {
            int numPlayers = Integer.parseInt(comboBox.getValue());
            clientController.setNumPlayers(numPlayers);
        });
        hbox.getChildren().addAll(comboBox, button);
        menuView.getNumPlayers().getChildren().addAll(text,hbox);
    }*/

    public void askNumToPlayer() {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            Stage currentStage =(Stage) root.getScene().getWindow();
            stage.initOwner(currentStage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Set number of players");

            VBox vBox = new VBox(10);
            vBox.setAlignment(Pos.CENTER);
            Label text = new Label();
            text.setAlignment(Pos.CENTER);
            text.setText("You are the first player, choose the number of player for this game:");
            HBox hbox = new HBox(5);
            hbox.setAlignment(Pos.CENTER);
            ComboBox<String> comboBox = new ComboBox<>();
            comboBox.getItems().addAll("2", "3", "4", "5");
            Button button = new Button("confirm");
            button.setOnAction(event -> {
                pool.submit(() -> {
                    if (!comboBox.getValue().equals(null)){
                        int numPlayers = Integer.parseInt(comboBox.getValue());
                        clientController.setNumPlayers(numPlayers);
                        System.out.println("DEBUG: il numero di giocatori è "+ numPlayers);
                    }
                    else{
                        event.consume();
                    }
                });
                /*new Thread(()-> {
                    int numPlayers = Integer.parseInt(comboBox.getValue());
                    clientController.setNumPlayers(numPlayers);
                    System.out.println("DEBUG: il numero di giocatori è "+ numPlayers);
                }).start();*/
                //cliccando il tasto conferma si chiude la finestra, non si può cambiare il numero di giocatori
                if (!comboBox.getValue().equals(null)){
                    stage.close();
                }
            });
            hbox.getChildren().addAll(comboBox, button);
            vBox.getChildren().addAll(text, hbox);

            Scene scene = new Scene(vBox, 400, 100);
            stage.setScene(scene);
            stage.setOnCloseRequest(event -> {
                event.consume();
            });
            stage.setResizable(false);
            stage.show();
        });
    }

    public void showPlayerFoodUpdate(String playerName, int food){
        Platform.runLater(() -> {
            Player player = clientController.getPlayerByName(playerName);
            panels.get(playerName).refreshFood(player);
        });
    }

    public void showPlayerPPUpdate(String playerName, int pp){
        Platform.runLater(() -> {
            Player player = clientController.getPlayerByName(playerName);
            panels.get(playerName).refreshPP(player);
        });
    }

    public void showMessage(String message){
        System.out.println("DEBUG: il messaggio è "+message);
        Platform.runLater(() -> {
            label.setText(message);
            label.setPrefWidth(400);
            label.setPrefHeight(100);
            label.setAlignment(Pos.CENTER);
            label.setStyle(
                    "-fx-background-color: white;" +
                            "-fx-text-fill: black;" +
                            "-fx-font-size: 15px;" +
                            "-fx-background-radius: 10;" +
                            "-fx-padding: 10;"
            );
            label.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(event -> {
                label.setVisible(false);
            });
            pause.play();
        });
    }

    public void showCurrentPlayer(String username) {

    }

    public void showAvailableTotems(ArrayList<Totem> availableTotems) {

    }

    public void showError(String message){
        System.out.println("DEBUG: il messaggio è "+message);
        Platform.runLater(() -> {
            label.setText(message);
            label.setPrefWidth(400);
            label.setPrefHeight(100);
            label.setAlignment(Pos.CENTER);
            label.setStyle(
                    "-fx-background-color: black;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-size: 15px;" +
                            "-fx-background-radius: 10;" +
                            "-fx-padding: 10;"
            );
            label.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(event -> {
                label.setVisible(false);
            });
            pause.play();
        });
    }

    public void showReturnToTOC(String username, int index){
        Platform.runLater(() -> {
           for(int i=0; i<boardView.getOCSlots().size(); i++){
               if(!boardView.getOCSlots().get(i).getChildren().isEmpty()){
                   Node thisTotemView = boardView.getOCSlots().get(i).getChildren().remove(0);
                   boardView.getTOCSlots().get(index).getChildren().add(thisTotemView);
                   break;
               }
           }
        });
    }
}
