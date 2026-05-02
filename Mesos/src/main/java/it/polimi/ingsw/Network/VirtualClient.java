package it.polimi.ingsw.Network;

import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface VirtualClient extends Remote {

    void showStartGame(String myName, ArrayList<Player> players, Totem myTotem, int myFood) throws RemoteException;

    void askNumPlayers() throws RemoteException;

    void showStartGame() throws RemoteException;

    void askForLogin() throws  RemoteException;

    void askForBuildingIndex(int row) throws RemoteException;

    void askForTotemMove() throws RemoteException;

    void showMyTurn() throws RemoteException;

    void askForRowBuildingIndex() throws RemoteException;

    void updateBoardtatus(Board board) throws RemoteException;

    void showCurrentPlayer(String playerName) throws RemoteException;

    void showError(String message) throws RemoteException;

    void updateOtherPlayerStatus(String playerName, List<Card> tribeCards, List<Building> buildings) throws RemoteException;

    void notifyAllPlayers(Player player, Event event) throws RemoteException;

    void showMessage(String message) throws RemoteException;

    void showEndGame(int finalScore) throws RemoteException;
}