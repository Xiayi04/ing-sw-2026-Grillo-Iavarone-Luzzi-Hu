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
    private final ClientRMI client;

    public RMIVirtualClient(ClientRMI client) throws RemoteException{
        this.client = client;
    }

    @Override
    public void askForLogin(List<Totem> availableTotems)throws RemoteException{
        client.askForLogin(availableTotems);
    }
    @Override
    void showStartGame(String myName, ArrayList<Player> players, Totem myTotem, int myFood) throws RemoteException{
        client.showStartGame(myName, players, myTotem, myFood);
    }
    @Override
    void askNumPlayers() throws RemoteException{
        client.askNumPlayers();
    }

    @Override
    void showStartGame() throws RemoteException{
        client.showStartGame();
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
        client.showMyTurn();
    }

    @Override
    void askForRowBuildingIndex() throws RemoteException{
        client.askForRowBuildingIndex();
    }

    @Override
    void updateBoardStatus(Board board) throws RemoteException{
        client.updateBoardStatus();
    }

    @Override
    void showCurrentPlayer(String playerName) throws RemoteException{
        client.showCurrentPlayer(playerName);
    }

    @Override
    void showError(String message) throws RemoteException{
        client.showError(message);
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
        client.(message);
    }

    @Override
    void showEndGame(int finalScore) throws RemoteException{
        client.showEndGame(finalScore);
    }


}




