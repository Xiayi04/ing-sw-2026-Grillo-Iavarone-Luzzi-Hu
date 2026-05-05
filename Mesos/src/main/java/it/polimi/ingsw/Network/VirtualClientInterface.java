package it.polimi.ingsw.Network;

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

public interface VirtualClientInterface extends Remote {
    //parametro passato dal server per dirmi che totem mostrare
    void askForLogin(List<Totem> availableTotems) throws  RemoteException;

    void askNumPlayers() throws RemoteException;

    void showStartGame(String myName, ArrayList<Player> players, Totem myTotem, int myFood) throws RemoteException;

    void askForTotemMove(ArrayList<OfferCard> path) throws RemoteException;

    void showMyTurn() throws RemoteException;

    void updateBoardStatus(Board board) throws RemoteException;

    void showCurrentPlayer(String playerName) throws RemoteException;

    void showError(String message) throws RemoteException;
    // se il server me lo aggiorna col board non mi serve questo metodo
    void updateOtherPlayerStatus(String playerName, List<Card> tribeCards, List<Building> buildings) throws RemoteException;

    void notifyAllPlayers(Player player, Event event) throws RemoteException;

    void showMessage(String message) throws RemoteException;

    void showEndGame() throws RemoteException;
}