package it.polimi.ingsw.Network;

import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Game.*;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface VirtualClientInterface extends Remote {
    //parametro passato dal server per dirmi che totem mostrare
    void showUpdateEra(int era) throws RemoteException;

    void updateForEvent(Event e) throws RemoteException;

    void returnTotemToTOC(Player player,int index) throws RemoteException;

    void showChosenNumPlayers(Player player) throws IOException;

    void updateFirstPlayer(Player player) throws RemoteException;

    void updateStartGame( ArrayList<Player> players, Board board) throws RemoteException;

    void setNumPlayers(int numPlayers) throws RemoteException;


    //void showError(String message) throws RemoteException;

    void showMessage(String message) throws RemoteException, IOException;

    void showEndGame(Player winner, List<PlayerScore> leaderboard) throws RemoteException;

    void refuseConnection() throws IOException;
    
    void newPlayer( Player newPlayer) throws IOException;


    void pickedCard(Player player, boolean row, boolean isBuilding, int index);

    void movedTotem(Player player, int index);

    void movedTotemError () throws RemoteException;

    void totemChoiceError () throws RemoteException;

    void updatePlayerFood(Player player, int update);

    void updatePlayerPP(Player player, int update);

    void showPlayerTurn(Player player);

    void updateNextTurn (String message) throws RemoteException;

    void pickCardError() throws RemoteException;

    void buildingPurchaseError() throws RemoteException;

    void totemPositionError() throws RemoteException;

    void usernameError() throws RemoteException;

    void totemNotAvailableError(ArrayList<Totem> availableTotems);

    void updateAvailableTotems(ArrayList<Totem> availableTotems);

    void numPlayersError() throws RemoteException;

    void updateConfirmedUsername(String username);

    void updateConfirmedTotem(Totem totem);


}