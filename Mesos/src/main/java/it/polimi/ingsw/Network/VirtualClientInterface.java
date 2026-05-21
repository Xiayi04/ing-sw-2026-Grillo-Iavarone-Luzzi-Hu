package it.polimi.ingsw.Network;

import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Game.*;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface VirtualClientInterface extends Remote {

    void showUpdateEra(int era) throws RemoteException;

    void updateForEvent(Event e) throws RemoteException;

    void returnTotemToTOC(String username,int index) throws RemoteException;

    void showChosenNumPlayers(int numPlayers) throws IOException;

    void updateFirstPlayer() throws RemoteException;

    void updateStartGame( ArrayList<Player> players, Board board) throws RemoteException;

    void setNumPlayers(int numPlayers) throws RemoteException;

    void showMessage(String message) throws RemoteException, IOException;

    void showEndGame(String winner, List<PlayerScore> leaderboard) throws RemoteException;

    void refuseConnection() throws IOException;

    void pickedCard(String username, boolean row, boolean isBuilding, int index);

    void movedTotem(String username, int index);

    void movedTotemError () throws RemoteException;

    void totemChoiceError () throws RemoteException;

    void updatePlayerFood(String username, int update);

    void updatePlayerPP(String username, int update);

    void showPlayerTurn(String username);

    void updateNextTurn (Board board) throws RemoteException;

    void pickCardError() throws RemoteException;

    void buildingPurchaseError() throws RemoteException;

    void totemPositionError() throws RemoteException;

    void usernameError() throws RemoteException;

    void totemNotAvailableError(ArrayList<Totem> availableTotems);

    void updateAvailableTotems(ArrayList<Totem> availableTotems);

    void numPlayersError() throws RemoteException;

    void updateConfirmedUsername(String username);

    void updateConfirmedTotem(Totem totem);

    void confirmNumPlayers(int numPlayers);

    void availableColors(ArrayList<Totem> availableTotems);
}