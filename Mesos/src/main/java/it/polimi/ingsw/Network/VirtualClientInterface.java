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


    void showStartGame(String myName, ArrayList<Player> players, Totem myTotem, int myFood) throws RemoteException;

    void showPlayerTurn(Player player) throws RemoteException;

    void newPlayer( Player newPlayer) throws RemoteException;

    void pickedCard(Player player, boolean row, boolean isBuilding, int index);

    void movedTotem(Player player, int index);

    void updatePlayerFood(Player player, int foodUpdateD);

    void updatePlayerPP(Player player, int PPUpdate);

    void showEndGame()throws RemoteException;

    void cardPickError()throws RemoteException;

    void buildingPurchaseError()throws RemoteException;

    void totemPositionError()throws RemoteException;

    //non serve update board(?)
}