package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Cards.Card;

import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.OfferCard;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface RemoteClientInterface extends Remote {

    void showMyTurn() throws RemoteException;

    void updateBoard(Board board) throws RemoteException;

   // void showCurrentPlayer(String playerName) throws RemoteException;

    void showError(String message) throws RemoteException;

   // void updateOtherPlayerStatus(String playerName, List<Card> tribeCards, List<Building> buildings) throws RemoteException;

    void showMessage(String message) throws RemoteException;

    void showEndGame() throws RemoteException;


    //void Login(List<Totem> availableTotems) throws  RemoteException;
    //void MoveTotem(ArrayList<OfferCard> path) throws RemoteException;
    //void pickCard() throws RemoteException;

}
