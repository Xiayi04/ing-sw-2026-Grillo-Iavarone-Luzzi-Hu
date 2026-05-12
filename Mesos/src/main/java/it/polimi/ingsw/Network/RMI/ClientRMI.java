package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Network.ClientController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ClientRMI extends UnicastRemoteObject implements RemoteClientInterface {

    private ClientController clientController;

    public ClientRMI (ClientController clientController) throws RemoteException {
        super();
        this.clientController = clientController;
    }

    @Override
    public void showMyTurn() throws RemoteException {
        clientController.showMyTurn();
    }

    @Override
    public void updateBoard(Board board) throws RemoteException {
        clientController.updateBoardStatus(board);
    }

    /*@Override
    public void showCurrentPlayer(String playerName) throws RemoteException {
        clientController.showCurrentPlayer(playerName);
    }*/

    @Override
    public void showError(String message) throws RemoteException {
        clientController.showError(message);
    }

    /*@Override
    public void updateOtherPlayerStatus(String playerName, List<Card> tribeCards, List<Building> buildings) throws RemoteException {
        clientController.updateOtherPlayerStatus(playerName, tribeCards, buildings);
    }*/

    @Override
    public void showMessage(String message) throws RemoteException {
        clientController.showMessage(message);
    }

    @Override
    public void showEndGame() throws RemoteException {
        clientController.showEndGame();
    }


}


