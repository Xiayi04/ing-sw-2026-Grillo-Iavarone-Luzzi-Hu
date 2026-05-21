package it.polimi.ingsw.Network.RMI;


import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.PlayerScore;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


public interface RemoteClientInterface extends Remote {
    void showGameStarted(ArrayList<Player> players, Board board) throws RemoteException;

    void showConfirmUsername(String username)throws RemoteException;

    void showConfirmTotem(Totem totem) throws RemoteException;

    void showTotemChoiceError(String message) throws RemoteException;

    void showUsernameError(String message) throws RemoteException;

    void numPlayersChosenError(String message) throws RemoteException;

    void showUpdateFirstPlayer(String player) throws RemoteException;

    void showChosenNumPlayers (int numPlayers) throws RemoteException;

    void showPlayerTurn(String player) throws RemoteException;

    void showPickedCard (String playerWhoPicked, boolean row, boolean isBuilding, int index) throws RemoteException;

    void showPickCardError(String message) throws RemoteException;

    void showTotemMoved(String player, int path) throws RemoteException;

    void showTotemMovedError(String message) throws RemoteException;

    void showLocalUpdateEra(int era) throws RemoteException;

    void showLocalReturnToTOC(String player,int index) throws RemoteException;

    void showUpdateForEvent(Event e) throws RemoteException;

    void updateNextTurn(Board board) throws RemoteException;

    void showAvailableTotems(ArrayList<Totem> availableTotems) throws RemoteException;

    void showUpdatePlayerFood(String player, int foodUpdated) throws RemoteException;

    void showUpdatePlayerPP(String player, int foodUpdated) throws RemoteException;

    void showEndGame(String winner, List<PlayerScore> leaderboard) throws RemoteException;


}
