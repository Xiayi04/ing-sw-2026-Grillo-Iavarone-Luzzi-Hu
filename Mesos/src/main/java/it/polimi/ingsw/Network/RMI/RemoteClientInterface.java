package it.polimi.ingsw.Network.RMI;


import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Network.PlayerScore;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface RemoteClientInterface extends Remote {
    void showStartGame() throws RemoteException;

    void showPlayerTurn(Player player) throws RemoteException;

    void addedPlayer (Player player) throws RemoteException;

    void showPickedCard (Player playerWhoPicked, boolean row, boolean isBuilding, int index) throws RemoteException;

    void movedTotem(Player player, int path) throws RemoteException;

    void foodUpdated(Player player, int foodUpdated) throws RemoteException;

    void updatePlayerPP(Player player, int foodUpdated) throws RemoteException;

    void showErrorMessage(String message) throws RemoteException;

    void showEndGame(Player winner, List<PlayerScore> leaderboard) throws RemoteException;

//
}
