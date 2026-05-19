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

    void askForLogin(List<Totem> availableTotems) throws  RemoteException;

    void askNumPlayers() throws IOException;

    void showStartGame( ArrayList<Player> players, Board board) throws RemoteException;

    void askForTotemMove(ArrayList<OfferCard> path) throws RemoteException;

    void askForLogin() throws IOException;

    void showMyTurn() throws RemoteException;

    void updateBoardStatus(Board board) throws RemoteException;

    void showCurrentPlayer(String playerName) throws RemoteException;

    void showError(String message) throws RemoteException;
    // se il server me lo aggiorna col board non mi serve questo metodo
    void updateOtherPlayerStatus(String playerName, List<Card> tribeCards, List<Building> buildings) throws RemoteException;

    void notifyAllPlayers(Player player, Event event) throws RemoteException;

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