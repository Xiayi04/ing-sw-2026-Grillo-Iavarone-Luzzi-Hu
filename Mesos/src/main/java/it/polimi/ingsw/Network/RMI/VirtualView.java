package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Cards.Card;

import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface VirtualView extends Remote {
    void notifyStartGame(String name, ArrayList<Player> players, Totem totem, int food) throws RemoteException;
    void notifyMyTurn() throws RemoteException;
    void updateBoardStatus(Board board) throws RemoteException;
    void showCurrentPlayer(String PlayerName) throws RemoteException;
    void showError(String message) throws RemoteException;
    void updateOtherPlayerStatus(String playerName, List<Card> tribeCards, List<Building> buildings)
            throws RemoteException;
    void endGame(int finalScore) throws RemoteException;

}
