package it.polimi.ingsw.Network;

import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Game.*;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface VirtualClientInterface extends Remote {
    /**
     * server notifies client that the era changed
     * @param era
     * @throws RemoteException if there is an error
     */
     void showUpdateEra(int era) throws RemoteException;

    /**
     *it notifies that the era ended and
     * @param e has to be solved by the players
     * @throws RemoteException if there is an error
     */
     void updateForEvent(Event e) throws RemoteException;
     /**
     * Notifies the client that after the events are resolved
     * a player's totem has been returned to the Turn Order Card.
     * @param username the username of the player whose totem is returned.
     * @param index the position on the Turn Order Card where the totem is placed.
     * @throws RemoteException if an error occurs.
     */
     void returnTotemToTOC(String username,int index) throws RemoteException;

    /**
     * server notifies that the
     * @param numPlayers chosen by the first player
     * @throws IOException if an error occurs
     */
     void showChosenNumPlayers(int numPlayers) throws IOException;

    /**
     *Notifies the client that the game has started and
     * the client updated is the first one
     * @throws RemoteException
     */
     void updateFirstPlayer() throws RemoteException;

    /**
     * Notifies that the game started , list of players and board are sent
     * @param players
     * @param board
     * @throws RemoteException
     */
     void updateStartGame( ArrayList<Player> players, Board board) throws RemoteException;

    /**
     * Notifies the client that the game ended and rhe list of the result
     * @param winner
     * @param leaderboard
     * @throws RemoteException
     */
     void showEndGame(String winner, List<PlayerScore> leaderboard) throws RemoteException;

    /**
     * Notifies the client that the connection request has been refused
     * @throws IOException
     */
     void refuseConnection() throws IOException;

    /**
     * Notifies the client that a player has picked a card from the board.
     * @param username of the player who picked the card
     * @param row  true shows if the card is from the upper row, false otherwise
     * @param isBuilding true if it is a building, false otherwise
     * @param index the position in which the card was picked
     */
     void pickedCard(String username, boolean row, boolean isBuilding, int index);
    /**
     * Notifies the client that a player has moved its totem.
     * @param username the username of the player who moved the totem.
     * @param index the totem position.
     */
     void movedTotem(String username, int index);
    /**
     * Notifies the client that  moveTotemRequest is not valid.
     * @throws RemoteException if a remote communication error occurs.
     */
    void movedTotemError () throws RemoteException;
    /**
     * Notifies the client that the chosen totem is not available because it's occupied.
     * @throws RemoteException if a remote communication error occurs.
     */

    void totemChoiceError () throws RemoteException;
    /**
     * Notifies the food value of the player
     * @param username the username of the player whose food value changed.
     * @param update the updated food value or the amount of food variation.
     */
    void updatePlayerFood(String username, int update);
    /**
     * Updates the prestige points of a player
     * @param username the username of the player w
     * @param update the updated pp
     */
    void updatePlayerPP(String username, int update);
    /**
     * Shows whose turn it currently is.
     * @param username the username of the current player.
     */
     void showPlayerTurn(String username);
    /**
     * Notifies the client that a new round has started and sends the updated board.
     * @param board the updated board
     * @throws RemoteException if an error occurs.
     */
     void updateNextRound (Board board) throws RemoteException;
    /**
     * Notifies the client that the card-picking request was invalid.
     * @throws RemoteException if an error occurs.
     */
     void pickCardError() throws RemoteException;
    /**
     * Notifies the client that the chosen username is already used.
     * @throws RemoteException if an error occurs.
     */
    void usernameError() throws RemoteException;
    /**
     * Sends the updated list of available totems to the client.
     * @param availableTotems the list of totems available
     */
    void updateAvailableTotems(ArrayList<Totem> availableTotems);
    /**
     * Notifies the client that the chosen number of players is not valid.
     * @throws RemoteException if an error occurs.
     */
    void numPlayersError() throws RemoteException;
    /**
     * Confirms to the client that the username has been accepted.
     * @param username the confirmed username.
     */
    void updateConfirmedUsername(String username);
    /**
     * Confirms to the client that the chosen totem has been accepted.
     * @param totem the confirmed totem.
     * @throws IOException if  error occurs.
     */
    void updateConfirmedTotem(Totem totem) throws IOException;
}