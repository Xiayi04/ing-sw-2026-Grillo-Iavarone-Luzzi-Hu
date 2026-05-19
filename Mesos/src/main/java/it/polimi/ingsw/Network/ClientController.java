package it.polimi.ingsw.Network;

import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.OfferCard;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.RMI.ClientRMI;
import it.polimi.ingsw.Network.Socket.Client.SocketClient;


import java.util.ArrayList;
// riferimento all'if che manda messaggi al server
// ho bisogno dei metodi chiamati dalla gui
// e quelli che aggiornano la gui

public class ClientController {
    private  GraphicInterface view;
    private  ServerConnection serverConnection;
    private  String tmpUsername = null;
    private  Totem tmpTotem = null;
    private  final Object tmpLock = new Object();
    private  Board currentBoard;
    private String localPlayerName;
    String rowName ;
    String cardType;

    public void setView(GraphicInterface view){
        this.view = view;
    }

    public void setConnection(ServerConnection serverConnection){
        this.serverConnection = serverConnection;
    }

    public void setServerConnection(boolean isRMI) {
        try {
            if (isRMI) {
                ClientRMI clientRMI = new ClientRMI("localhost", 1099, "VirtualServer");
                clientRMI.setClientController(this);
                clientRMI.run();

                this.serverConnection = clientRMI;
            } else {
                SocketClient socketClient = new SocketClient(this);
                this.serverConnection = socketClient;
            }
        } catch (Exception e) {
            view.showErrorMessage("Connection error.");
            e.printStackTrace();
        }
    }

    public Board getCurrentBoard() {
        return currentBoard;
    }

    public void setLocalPlayerName(String localPlayerName) {
        this.localPlayerName = localPlayerName;
    }

    public Player getLocalPlayer() {
        for(Player p : currentBoard.getPlayers()){
            if(p.getName().equals(localPlayerName)){
                return p;
            }
        }
        return null;
    }

    public void setCurrentBoard(Board currentBoard) {
        this.currentBoard = currentBoard;
    }

    /* public void login(String username, Totem chosenTotem) {
        if (username == null || username.isBlank()) {
            view.showError("Username not valid.");
            return;
        }

        if (chosenTotem == null) {
            view.showError("Totem not valid.");
            return;
        }
        serverConnection.login(username, chosenTotem);
    }*/


    public void setNumPlayers(int numPlayers) {
        while (numPlayers < 2 || numPlayers > 5) {
            view.showError("Number of Players is wrong ");
            numPlayers = view.askNumToPlayer();
        }
        serverConnection.setNumPlayers(numPlayers);
    }

    public void chooseTotemPosition(ArrayList<OfferCard> path) {

        if (path == null || path.isEmpty()) {
            view.showError("Offer card path not available.");
            return;
        }

        int chosenPosition = view.askPosition(path);

        while (chosenPosition < 0 || chosenPosition >= path.size()) {
            view.showError("Position not valid.");
            chosenPosition = view.askPosition(path);
        }

        serverConnection.setTotemPosition(chosenPosition);
    }

    public void PickCard(boolean isUpper, boolean isBuilding, int index) {

        if (isUpper) {
            if (!(index >= 0 && index < currentBoard.getUpperCardRow().size())) {
                view.showError("Card index not valid.");

            } else {
                if (!(index >= 0 && index < currentBoard.getLowerCardsRow().size())) {
                    view.showError("Card index not valid.");
                    return;
                }
            }
            serverConnection.requestPickCard(isUpper, isBuilding, index);
        }
    }

    public void leave() {
        serverConnection.leave();
    }

    //aggiornano la gui


    public void showStartGame() {
        view.showMessage("The game started.");
    }

    public void showPlayerTurn(Player player) {
        view.showMessage("It's your turn.");
    }

    public void addedPlayer(Player player) {
        view.showMessage("New player added: "+player);
    }
    public void showPickedCard(Player playerWhoPicked, boolean row, boolean isBuilding, int index){
        rowName = row? "upperRow" : "lowerRow";
        cardType = isBuilding ? "building" : "character";
        view.showMessage(playerWhoPicked + "has taken" + cardType + "from" + rowName);
    }



    public void movedTotem(Player player, int path){
        view.showMessage("Player"+ player + "moved to: "+ path);
    }
    public void foodUpdated(Player player, int foodUpdated){
        view.showMessage(player + "'s food + "+foodUpdated);
    }
    public void updatePlayerPP(Player player, int PPUpdated){
        view.showMessage(player + "'s PP updated: " + PPUpdated);
    }

    public void updateBoardStatus(Board updatedBoard) {
        this.currentBoard = updatedBoard;
        view.updateBoardStatus(currentBoard);
    }
    public void showErrorMessage(String message) {
        view.showError(message);
    }


    public void showEndGame() {
        view.showMessage("Game ended.");
    }


    //Login management

    public void setTmpUsername(String username) {
        new Thread(() -> {
            synchronized (tmpLock){
                if (username == null || username.isBlank()) {

                }
                tmpUsername = username;
                checkForLogin();
            }
        }).start();


    }

    public void setTmpTotem(Totem totem) {
        new Thread(() -> {
            synchronized (tmpLock){
                if (tmpTotem == null) {

                }
                tmpTotem = totem;
                checkForLogin();
            }
        }).start();
    }

    public void checkForLogin(){
        synchronized (tmpLock){
            if(tmpTotem == null && tmpUsername==null){
                return;
            }
            serverConnection.login(tmpUsername, tmpTotem);
            tmpUsername = null;
            tmpTotem = null;
        }
    }
}
