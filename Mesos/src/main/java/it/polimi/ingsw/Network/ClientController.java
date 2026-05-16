package it.polimi.ingsw.Network;


import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.OfferCard;
import it.polimi.ingsw.Game.Totem;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ClientController {
    private final GraphicInterface view;
    //riferimento all'if che manda messaggi al server
    private final ServerConnection serverConnection;
    private String tmpUsername = null;
    private Totem tmpTotem = null;
    private final Object tmpLock = new Object();

    public ClientController(GraphicInterface view, ServerConnection serverConnection) {
        this.view = view;
        this.serverConnection = serverConnection;
    }

    private Board currentBoard;


    public void login(String username, Totem chosenTotem) {
        if (username == null || username.isBlank()) {
            view.showError("Username not valid.");
            return;
        }

        if (chosenTotem == null) {
            view.showError("Totem not valid.");
            return;
        }
        serverConnection.login(username, chosenTotem);
    }


    public void SetNumPlayers(int numPlayers) {
        while (numPlayers < 2 || numPlayers > 5) {
            view.showError("Number of Players is wrong ");
            numPlayers = view.askNumToPlayer();
        }
        serverConnection.setNumPlayers(numPlayers);
    }

    public void moveTotem(ArrayList<OfferCard> path) {

        if (path == null || path.isEmpty()) {
            view.showError("Offer card path not available.");
            return;
        }

        int chosenPosition = view.askPosition(path);

        while (chosenPosition < 0 || chosenPosition >= path.size()) {
            view.showError("Position not valid.");
            chosenPosition = view.askPosition(path);
        }

        serverConnection.moveTotem(chosenPosition);
    }

    public void pickCard(boolean isUpper, boolean isBuilding, int index) {


        if (index < 0) {
            view.showError("Card index not valid.");
            return;
        }

        serverConnection.pickCard(isUpper, isBuilding, index);
    }

    public void leave() {
        serverConnection.leave();
    }

    public void showMessage(String message) {
        view.showMessage(message);
    }

    public void showError(String message) {
        view.showError(message);
    }

    public void showStartGame() {
        view.showMessage("The game started.");
    }

    public void showMyTurn() {
        view.showMessage("It's your turn.");
    }

    public void updateBoardStatus(Board updatedBoard) {
        this.currentBoard = updatedBoard;
        view.updateBoardStatus(currentBoard);
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
