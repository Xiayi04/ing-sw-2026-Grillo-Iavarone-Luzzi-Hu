package it.polimi.ingsw.UI.GUI;


import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Database.LeaderBoardData;
import it.polimi.ingsw.Game.OfferCard;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.GraphicInterface;
import it.polimi.ingsw.Network.PlayerScore;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuiView implements GraphicInterface {
    private final ClientController clientController;
    private final BoardView boardView;
    private final MenuView menuView;
    private final Map<String, Playerpanel> panels = new HashMap<>();
    private final StackPane root;
    private final VBox vboxMessage = new  VBox();
    private final Label currentPlayerLabel = new Label();
    private final ExecutorService pool = Executors.newCachedThreadPool();
    //private final Label labelError = new Label("prova");
    private final Label label = new Label();
    private final TableView<LeaderBoardData> leaderboardTable = new TableView<>();
    private final TableColumn<LeaderBoardData, Integer> positionColumn = new TableColumn<>("Position");
    private final TableColumn<LeaderBoardData, String> usernameColumn = new TableColumn<>("Username");
    private final TableColumn<LeaderBoardData, Integer> scoreColumn = new TableColumn<>("Score");
    private final TableColumn<LeaderBoardData, String> dateColumn = new TableColumn<>("Date");
    private int highlightedPosition;

    public GuiView(ClientController clientController, StackPane root) {
        this.clientController = clientController;
        this.menuView = new MenuView(clientController, this, pool);
        this.boardView = new BoardView(clientController, this, pool);
        this.root = root;
    }

    public void showMainStage() {
        //System.out.println("DEBUG: metodo showMainStage chiamato");
        root.getChildren().clear();
        ImageView background = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/background/background.png"))));
        background.setPreserveRatio(false);
        background.setFitWidth(root.getWidth());
        background.setFitHeight(root.getHeight());
        background.fitWidthProperty().bind(root.widthProperty().multiply(1.05));
        background.fitHeightProperty().bind(root.heightProperty().multiply(1.05));
        background.setEffect(new GaussianBlur(20));
        root.getChildren().add(background);
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(showPlayersBar());
        boardView.showBoard();
        borderPane.setCenter(boardView);
        borderPane.setBottom(showLocalPlayer());
        root.getChildren().add(borderPane);
        root.getChildren().add(vboxMessage);
        vboxMessage.setMouseTransparent(true);
        vboxMessage.setAlignment(Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(vboxMessage, Pos.BOTTOM_RIGHT);
        currentPlayerLabel.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: red;" +
                "-fx-font-size: 50px;" +
                "-fx-font-weight: bold;" +
                "-fx-effect: dropshadow(gaussian, red, 6, 0.2, 0, 0);"
        );
        currentPlayerLabel.setVisible(false);
        currentPlayerLabel.setMouseTransparent(true);
        root.getChildren().add(currentPlayerLabel);
        ImageView help = Utils.createImageView("/images/logo/help.png", 40);
        help.setCursor(Cursor.HAND);
        help.setOnMouseClicked(event -> {
            pool.submit(() -> {
                this.openManual();
            });
        });
        root.getChildren().add(help);
        StackPane.setAlignment(help, Pos.TOP_LEFT);
    }

    public void showLobbyMenu() {
        root.setStyle("-fx-background-color: red");
        /*ImageView background = new ImageView(new Image(new File("images/background/background.png").toURI().toString()));
        background.setPreserveRatio(false);
        background.setFitWidth(root.getWidth());
        background.setFitHeight(root.getHeight());
        background.fitWidthProperty().bind(root.widthProperty().multiply(1.05));
        background.fitHeightProperty().bind(root.heightProperty().multiply(1.05));
        background.setEffect(new GaussianBlur(20));
        root.getChildren().add(background);*/
        BorderPane borderPane = new BorderPane();
        menuView.createSchema();
        borderPane.setCenter(menuView);
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        //hBox.getChildren().addAll(label/*labelError*/);
        borderPane.setBottom(hBox);
        root.getChildren().add(borderPane);
        root.getChildren().add(vboxMessage);
        vboxMessage.setMouseTransparent(true);
        vboxMessage.setAlignment(Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(vboxMessage, Pos.BOTTOM_RIGHT);
        //borderPane.setRight(vboxMessage);
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
                panels.put(p.getName(), localPlayer);
                localPlayer.setAlignment(Pos.CENTER);
                localPlayer.setPadding(new Insets(0, 0, 20, 0));
                return localPlayer;
            }
        }
        return null;
    }

    public void pickCard(String username, boolean isUpper, boolean isBuilding, int index){
        Platform.runLater(() -> {
            if(index<0)
                throw new IllegalArgumentException("Invalid index");
            if(isUpper){
                if(isBuilding){
                    //boardView.getUpperBuildingRow().getChildren().remove(index);
                    refreshUpperBuildingCards();
                }
                else{
                    //boardView.getUpperCharacterRow().getChildren().remove(index);
                    refreshUpperCards();
                }
            }
            else{
                if(isBuilding){
                    //boardView.getDownBuildingRow().getChildren().remove(index);
                    refreshDownBuildingCards();
                }
                else{
                    //boardView.getDownCharacterRow().getChildren().remove(index);
                    refreshDownCards();
                }
            }
        });
    }

    public void moveTotem(String username, int index) {
        //System.out.println("DEBUG: totem mosso");
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

    public void refreshUpperCards(){
        boardView.getUpperCharacterRow().getChildren().clear();
        for(Card c : clientController.getCurrentBoard().getUpperCardRow()){
            ImageView cardView = Utils.createImageView(c.getImagePath(), 150);
            cardView.setCursor(Cursor.HAND);
            cardView.setOnMouseClicked(event ->
            {
                pool.submit(() -> {
                    clientController.requestLocalPickCard(true, false, boardView.getUpperCharacterRow().getChildren().indexOf(cardView));
                });
            });
            cardView.setOnMouseEntered(event -> {
                cardView.setStyle(" -fx-scale-x: 1.2; -fx-scale-y: 1.2;");
            });
            cardView.setOnMouseExited(event -> {
                cardView.setStyle("");
            });
            boardView.getUpperCharacterRow().getChildren().add(cardView);
        }
    }

    public void refreshDownCards(){
        boardView.getDownCharacterRow().getChildren().clear();
        for(Card c : clientController.getCurrentBoard().getLowerCardsRow()){
            ImageView cardView = Utils.createImageView(c.getImagePath(), 150);
            cardView.setCursor(Cursor.HAND);
            cardView.setOnMouseClicked(event -> {
                pool.submit(() -> {
                    clientController.requestLocalPickCard(false, false, boardView.getDownCharacterRow().getChildren().indexOf(cardView));
                });
            });
            cardView.setOnMouseEntered(event -> {
                cardView.setStyle("-fx-scale-x: 1.2; -fx-scale-y: 1.2;");
            });
            cardView.setOnMouseExited(event -> {
                cardView.setStyle("");
            });
            boardView.getDownCharacterRow().getChildren().add(cardView);
        }
    }

    public void refreshUpperBuildingCards(){
        boardView.getUpperBuildingRow().getChildren().clear();
        for(Card c : clientController.getCurrentBoard().getUpperBuildingRow()){
            ImageView buildingView = Utils.createImageView(c.getImagePath(), 150);
            buildingView.setCursor(Cursor.HAND);
            buildingView.setOnMouseClicked(event ->{
                pool.submit(() -> {
                    clientController.requestLocalPickCard(true, true, boardView.getUpperBuildingRow().getChildren().indexOf(buildingView));
                });
            });
            buildingView.setOnMouseEntered(event -> {
                buildingView.setStyle(" -fx-scale-x: 1.2; -fx-scale-y: 1.2;");
            });
            buildingView.setOnMouseExited(event -> {
                buildingView.setStyle("");
            });
            boardView.getUpperBuildingRow().getChildren().add(buildingView);
        }
    }

    public void refreshDownBuildingCards(){
        boardView.getDownBuildingRow().getChildren().clear();
        for(Card c : clientController.getCurrentBoard().getLowerBuildingRow()){
            ImageView buildingView = Utils.createImageView(c.getImagePath(), 150);
            buildingView.setCursor(Cursor.HAND);
            buildingView.setOnMouseClicked(event ->{
                pool.submit(() -> {
                    clientController.requestLocalPickCard(false, true, boardView.getDownBuildingRow().getChildren().indexOf(buildingView));
                });
            });
            buildingView.setOnMouseEntered(event -> {
                buildingView.setStyle("-fx-scale-x: 1.2; -fx-scale-y: 1.2;");
            });
            buildingView.setOnMouseExited(event -> {
                buildingView.setStyle("");
            });
            boardView.getDownBuildingRow().getChildren().add(buildingView);
        }
    }

    public void showNextRound(){
        //System.out.println("DEBUG: metodo  showNextRound chiamato");
        Platform.runLater(() -> {
            refreshUpperCards();
            refreshUpperBuildingCards();
            refreshDownCards();
            refreshDownBuildingCards();
            boardView.getDeck().getChildren().clear();
            if(!clientController.getCurrentBoard().getDeck().isEmpty()){
                Card c = clientController.getCurrentBoard().getDeck().getFirst();
                ImageView imageView = Utils.createImageView("/images/cards/back/era"+c.getEra()+".png", 150);
                boardView.getDeck().getChildren().add(imageView);
            }
            updateReferences();
        });
    }

    public void updateReferences(){
        for(Player p : clientController.getCurrentBoard().getPlayers()){
            panels.get(p.getName()).setPlayer(p);
        }
        for(Player p : clientController.getCurrentBoard().getPlayers()){
            panels.get(p.getName()).refreshFood(p);
            panels.get(p.getName()).refreshPP(p);
        }
    }

    public void showStartGame() {
        Platform.runLater(() -> {
            this.showMainStage();
        });
    }

    @Override
    public void showEndGameSuccessfully(String winner, List<PlayerScore> leaderboard) {
        System.out.println("il metodo showEndGameSuccessfully è stato chiamato");
        Platform.runLater(() -> {
            root.getChildren().clear();
            ImageView background = new ImageView(new Image(new File("images/background/background.png").toURI().toString()));
            background.setPreserveRatio(false);
            background.setFitWidth(root.getWidth());
            background.setFitHeight(root.getHeight());
            background.fitWidthProperty().bind(root.widthProperty().multiply(1.05));
            background.fitHeightProperty().bind(root.heightProperty().multiply(1.05));
            background.setEffect(new GaussianBlur(20));
            root.getChildren().add(background);
            //root.setStyle("-fx-background-color: red");
            VBox scoreboard = new VBox(10);
            scoreboard.setStyle("-fx-background-color: black");
            scoreboard.setAlignment(Pos.CENTER);
            ArrayList<Label> labels = new ArrayList<>();
            for(int i=0; i<leaderboard.size(); i++){
                PlayerScore p = leaderboard.get(i);
                Label position = new Label();
                position.setText(i+1+"°" + p.username() + "  -  " + p.points() + "  -  " + p.food());
                switch(i){
                    case 0:
                        position.setStyle(
                                "-fx-font-size: 36px;" +
                                        "-fx-font-weight: bold;" +
                                        "-fx-text-fill: gold;"
                        );
                        break;
                    case 1:
                        position.setStyle(
                                "-fx-font-size: 36px;" +
                                        "-fx-font-weight: bold;" +
                                        "-fx-text-fill: silver;"
                        );
                        break;
                    case 2:
                        position.setStyle(
                                "-fx-font-size: 36px;" +
                                        "-fx-font-weight: bold;" +
                                        "-fx-text-fill: #cd7f32;"
                        );
                        break;
                    default:
                        position.setStyle(
                                "-fx-font-size: 36px;" +
                                        "-fx-font-weight: bold;" +
                                        "-fx-text-fill: white;"
                        );
                        break;
                }
                position.setOpacity(0);
                labels.add(position);
                scoreboard.getChildren().add(position);
            }
            root.getChildren().add(scoreboard);
            StackPane.setAlignment(scoreboard, Pos.CENTER);
            SequentialTransition sequence = new SequentialTransition();
            for(int i= labels.size()-1; i>=0; i--){
                Label label = labels.get(i);
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), label);
                fadeTransition.setFromValue(0);
                fadeTransition.setToValue(1);
                sequence.getChildren().add(fadeTransition);
            }
            sequence.play();
        });
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
            Button button = new Button("Confirm");
            button.setOnAction(event -> {
                pool.submit(() -> {
                    if (!comboBox.getValue().equals(null)){
                        int numPlayers = Integer.parseInt(comboBox.getValue());
                        clientController.setNumPlayers(numPlayers);
                        //System.out.println("DEBUG: il numero di giocatori è "+ numPlayers);
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
            Label label = new Label();
            label.setText(message);
            label.setPrefWidth(400);
            label.setPrefHeight(100);
            label.setAlignment(Pos.CENTER);
            label.setStyle(
                    "-fx-background-color: white;" +
                    "-fx-text-fill: black;" +
                    "-fx-font-size: 15px;" +
                    "-fx-background-radius: 10;" +
                    "-fx-padding: 10;" +
                    "-fx-border-color: black;" +
                    "-fx-border-radius: 10;" +
                    "-fx-border-width: 4;"
            );
            vboxMessage.getChildren().add(label);
            if(vboxMessage.getChildren().size()>3){
                vboxMessage.getChildren().remove(0);
            }
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(event -> {
                vboxMessage.getChildren().remove(label);
            });
            pause.play();
        });
    }

    public void showCurrentPlayer(String username) {
        Platform.runLater(() -> {
            //currentPlayerLabel.setText("PROVA");
            if(username.equals(clientController.getLocalPlayer().getName())){
                currentPlayerLabel.setText("It's your turn");
            }
            else{
                currentPlayerLabel.setText("It's " + username +  "'s turn");
            }
            currentPlayerLabel.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(event -> {
                currentPlayerLabel.setVisible(false);
            });
            pause.stop();
            pause.playFromStart();
        });
    }

    public void showAvailableTotems(ArrayList<Totem> availableTotems) {

    }

    public void showError(String message){
        System.out.println("DEBUG: l'errore  è "+message);
        Platform.runLater(() -> {
            Label label = new Label();
            label.setText(message);
            label.setPrefWidth(400);
            label.setPrefHeight(100);
            label.setAlignment(Pos.CENTER);
            label.setStyle(
                    "-fx-background-color: black;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-size: 15px;" +
                            "-fx-background-radius: 10;" +
                            "-fx-padding: 10;" +
                            "-fx-border-color: white;" +
                            "-fx-border-radius: 10;" +
                            "-fx-border-width: 4;"
            );
            vboxMessage.getChildren().add(label);
            if(vboxMessage.getChildren().size()>3){
                vboxMessage.getChildren().remove(0);
            }
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(event -> {
                vboxMessage.getChildren().remove(label);
            });
            pause.play();
        });
    }

    public void showReturnToTOC(String username){
        //System.out.println("DEBUG: metodo showReturnToTOC chiamato");
        Platform.runLater(() -> {
           Node thisTotemView = null;
            for(int i=0; i<boardView.getOCSlots().size(); i++){
               if(!boardView.getOCSlots().get(i).getChildren().isEmpty()){
                   thisTotemView = boardView.getOCSlots().get(i).getChildren().remove(0);
                   break;
               }
           }
           for(int i=0; i<boardView.getTOCSlots().size(); i++){
               if(boardView.getTOCSlots().get(i).getChildren().isEmpty()){
                   boardView.getTOCSlots().get(i).getChildren().add(thisTotemView);
                   break;
               }
           }
        });
    }

    public void initializeLeaderboardDB(){
        positionColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().position()));
        usernameColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().username()));
        scoreColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().score()));
        dateColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().date()));
        leaderboardTable.getColumns().addAll(positionColumn, usernameColumn, scoreColumn, dateColumn);
        positionColumn.prefWidthProperty().bind(leaderboardTable.widthProperty().multiply(0.10));
        usernameColumn.prefWidthProperty().bind(leaderboardTable.widthProperty().multiply(0.30));
        scoreColumn.prefWidthProperty().bind(leaderboardTable.widthProperty().multiply(0.20));
        dateColumn.prefWidthProperty().bind(leaderboardTable.widthProperty().multiply(0.40));
        leaderboardTable.prefWidthProperty().bind(root.widthProperty().multiply(0.85));
        leaderboardTable.prefHeightProperty().bind(root.heightProperty().multiply(0.75));
        leaderboardTable.maxWidthProperty().bind(root.widthProperty().multiply(0.85));
        leaderboardTable.maxHeightProperty().bind(root.heightProperty().multiply(0.75));
        leaderboardTable.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(LeaderBoardData item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setStyle("");
                } else if (item.position() == highlightedPosition && item.username().equals(clientController.getLocalPlayerName())) {
                    setStyle("-fx-background-color: gold; -fx-font-weight: bold;");
                } else {
                    setStyle("");
                }
            }
        });
    }

    @Override
    public void showLeaderboardFromDB(int playerPosition, List<LeaderBoardData> updatedDB) {
        Platform.runLater(() -> {
            root.getChildren().clear();
            root.setStyle("-fx-background-color: red");
            highlightedPosition = playerPosition;
            System.out.println("DEBUG: highlightedPosition: " + highlightedPosition);
            initializeLeaderboardDB();
            leaderboardTable.getItems().setAll(updatedDB);
            root.getChildren().add(leaderboardTable);
        });
    }
}