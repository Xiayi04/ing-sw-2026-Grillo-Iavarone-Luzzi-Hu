package it.polimi.ingsw.Network;

import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Game.Board;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface VirtualClientInterface extends Remote {

    void showMyTurn() throws RemoteException;

    void updateBoard(Board board) throws RemoteException;

    void showError(String message) throws RemoteException;

    void showMessage(String message) throws RemoteException;

    void showEndGame() throws RemoteException;

}