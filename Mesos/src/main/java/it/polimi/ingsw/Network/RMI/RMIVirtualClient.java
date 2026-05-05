package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RMIVirtualClient extends UnicastRemoteObject implements VirtualClientInterface {
//riceve le richieste dal server
    private final ClientController clientController;

    public RMIVirtualClient(ClientController clientController) throws RemoteException{
        this.clientController = clientController;
    }

    @Override
    public void askForLogin(List<Totem> availableTotems)throws RemoteException{
        clientController.askForLogin(availableTotems);
    }
    @Override
    void showStartGame(String myName, ArrayList<Player> players, Totem myTotem, int myFood) throws RemoteException{
        clientController.showStartGame(myName, players, myTotem, myFood);
    }
    @Override
    void askNumPlayers() throws RemoteException{
        clientController.askNumPlayers();
    }

    @Override
    void showStartGame() throws RemoteException{
        clientController.showStartGame();
    }

    /*@Override
    void askForBuildingIndex(int row) throws RemoteException{
        clientController.askForBuildingIndex();
    }

    @Override
    void askForTotemMove() throws RemoteException{
        clientController.askForMoveTotem();
    }*/

    @Override
    void showMyTurn() throws RemoteException{
        clientController.showMyTurn();
    }

    @Override
    void askForRowBuildingIndex() throws RemoteException{
        clientController.askForRowBuildingIndex();
    }

    @Override
    void updateBoardStatus(Board board) throws RemoteException{
        clientController.updateBoardStatus();
    }

    @Override
    void showCurrentPlayer(String playerName) throws RemoteException{
        clientController.showCurrentPlayer(playerName);
    }

    @Override
    void showError(String message) throws RemoteException{
        clientController.showError(message);
    }

   /* @Override
    void updateOtherPlayerStatus(String playerName, List<Card> tribeCards, List<Building> buildings) throws RemoteException{
        clientController.updateOtherPlayerStatus(playerName,tribeCards, buildings);
    }

    @Override
    void notifyAllPlayers(Player player, Event event) throws RemoteException{
        clientController.notifyAllPlayers(player, event);
    }*/

    @Override
    void showMessage(String message) throws RemoteException{
        clientController.(message);
    }

    @Override
    void showEndGame(int finalScore) throws RemoteException{
        clientController.showEndGame(finalScore);
    }


}




