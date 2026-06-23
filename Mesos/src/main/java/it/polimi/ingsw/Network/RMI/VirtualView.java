package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Model.Cards.Buildings.Building;
import it.polimi.ingsw.Model.Cards.Card;

import it.polimi.ingsw.Model.Game.Board;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Totem;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface VirtualView extends Remote {
    void showStartGame(String name, ArrayList<Player> players, Totem totem, int food) throws RemoteException;
    void showMyTurn() throws RemoteException;
    void updateBoardStatus(Board board) throws RemoteException;
    void showCurrentPlayer(String PlayerName) throws RemoteException;
    void showError(String message) throws RemoteException;
    void updateOtherPlayerStatus(String playerName, List<Card> tribeCards, List<Building> buildings)
            throws RemoteException;
    void showEndGame(int finalScore) throws RemoteException;

}
