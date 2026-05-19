package it.polimi.ingsw.Network;

import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.OfferCard;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface VirtualClientInterface extends Remote {
    //parametro passato dal server per dirmi che totem mostrare
    void updateEra(int era) throws RemoteException;

    void resolvingEvent(Event e) throws RemoteException;

    void returnedTotemOnTurnOrder(Player player,int index);

    void askNumPlayers() throws IOException;

    void showStartGame( ArrayList<Player> players, Board board) throws RemoteException;


    void showError(String message) throws RemoteException;

    void showMessage(String message) throws RemoteException, IOException;

    void showEndGame() throws RemoteException;

    void refuseConnection() throws IOException;
    
    void newPlayer( Player newPlayer) throws IOException;


    void pickedCard(Player player, boolean row, boolean isBuilding, int index);

    void movedTotem(Player player, int index);

    void updatePlayerFood(Player player, int update);

    void updatePlayerPP(Player player, int update);

    void showPlayerTurn(Player player);

    void cardPickError();

    void buildingPurchaseError();

    void totemPositionError();

    void totemNotAvailableError(ArrayList<Totem> availableTotems);

    void setNumPlayersError();

    void confirmUsername(String username);

    void confirmTotem(Totem totem);

    void availableColors(ArrayList<Totem> availableTotems);
}