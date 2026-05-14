package it.polimi.ingsw.Network;


import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.OfferCard;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ClientController {
    private final GraphicInterface view;
    //riferimento all'if che manda messaggi al server
    private final ServerConnection serverConnection;
    private Board currentBoard;

    public ClientController(GraphicInterface view, ServerConnection serverConnection) {
        this.view = view;
        this.serverConnection = serverConnection;
        this.currentBoard = currentBoard;
    }




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

        serverConnection.setTotemPosition(chosenPosition);
    }

    public void pickCard(String username, boolean isUpper, boolean isBuilding, int index) {
        if (index < 0) {
            view.showError("Card index not valid.");
            return;
        }
        serverConnection.pickCard(username, isUpper, isBuilding, index);
    }

    public void leave() {
        serverConnection.leave();
    }
//metodi chiamati dal server

    public void showStartGame() {
       view.showMessage("The game started.");
    }

   /*  public void showMessage(String message) {
        view.showMessage(message);
    }

    public void showError(String message) {
        view.showError(message);
    }*/
    public void showPlayerTurn(Player player) {
        if (player == null) {
            view.showError("Current player not valid.");
            return;
        }
        view.showMessage("It's " + player + "'s turn.");
    }
    public void addedPlayer(Player player) {
        if (player == null) {
            view.showError("Added player not valid.");
            return;
        }

        view.showMessage("New player added: " + player);
    }
    public void showPickedCard(Player playerWhoPicked, boolean row, boolean isBuilding, int index) {
        if (playerWhoPicked == null) {
            view.showError("Player who picked card is not valid.");
            return;
        }

        String rowName = row ? "upper row" : "lower row";
        String cardType = isBuilding ? "building" : "card";

        view.showMessage(playerWhoPicked + " picked a " + cardType + " from " + rowName + " at index " + index + ".");
    }


    public void movedTotem(Player player, int pathPosition) {
        if (player == null) {
            view.showError("Player who moved totem is not valid.");
            return;
        }

        view.showMessage(player + " moved totem to position " + pathPosition + ".");
    }

    public void foodUpdated(Player player, int foodUpdated) {
        if (player == null) {
            view.showError("Player food update not valid.");
            return;
        }

        view.showMessage(player + "'s food updated: " + foodUpdated + ".");
    }

    public void updatePlayerPP(Player player, int ppUpdated) {
        if (player == null) {
            view.showError("Player PP update not valid.");
            return;
        }

        view.showMessage(player + "'s PP updated: " + ppUpdated + ".");
    }

    public void showErrorMessage(String message) {
        view.showError(message);
    }

    public void updateBoardStatus(Board updatedBoard) {
        if (updatedBoard == null) {
            view.showError("Board update not valid.");
            return;
        }

        this.currentBoard = updatedBoard;
        view.updateBoardStatus(currentBoard);
    }

    public void showEndGame() {
        view.showMessage("Game ended.");
    }
}
