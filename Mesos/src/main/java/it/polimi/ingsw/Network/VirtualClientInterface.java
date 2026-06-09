package it.polimi.ingsw.Network;

import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Database.LeaderBoardData;
import it.polimi.ingsw.Game.*;
import java.util.ArrayList;
import java.util.List;

public interface VirtualClientInterface {
    void skipTurn();

    /**
     * server notifies client that the era changed
     * @param era:
     */
     void showUpdateEra(int era);

    /**
     *it notifies that the era ended and
     * @param e has to be solved by the players
     */
     void updateForEvent(Event e) ;
     /**
     * Notifies the client that after the events are resolved
     * a player's totem has been returned to the Turn Order Card.
     * @param username the username of the player whose totem is returned.
     * @param index the position on the Turn Order Card where the totem is placed.
     */
     void returnTotemToTOC(String username,int index);

    /**
     * server notifies the
     * @param numPlayers chosen by the first player
     */
     void showChosenNumPlayers(int numPlayers) ;

    /**
     *Notifies the client that the game has started and
     * the client updated is the first one
     */
     void updateFirstPlayer();

    /**
     * Notifies that the game started , list of players and board are sent
     * @param players:
     * @param board:
     */
     void updateStartGame( ArrayList<Player> players, Board board);

    /**
     * Notifies the client that the game successfully ended and The list of the result
     * @param winner:
     * @param leaderboard:
     */
     void updateEndGame(String winner, List<PlayerScore> leaderboard) ;

    /**
     * Notifies the client that the connection request has been refused,
     * because of too many players
     */
     void refuseConnection() ;

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
     */
    void movedTotemError () ;

    /**
     * Notifies the client that the chosen totem is not available because it's occupied.
     */

    void totemChoiceError ();
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
     */
     void updateNextRound (Board board);
    /**
     * Notifies the client that the card-picking request was invalid.
     */
     void pickCardError();
    /**
     * Notifies the client that the chosen username is already used.
     */
    void usernameError() ;
    /**
     * Sends the updated list of available totems to the client.
     * @param availableTotems the list of totems available
     */
    void updateAvailableTotems(ArrayList<Totem> availableTotems);
    /**
     * Notifies the client that the chosen number of players is not valid.
     */
    void numPlayersError();
    /**
     * Confirms to the client that the username has been accepted.
     * @param username the confirmed username.
     */
    void updateConfirmedUsername(String username);
    /**
     * Confirms to the client that the chosen totem has been accepted.
     * @param totem the confirmed totem.
     */
    void updateConfirmedTotem(Totem totem) ;

    /**
     *
     */
    void ping() ;

    /**
     * Lets the client know that the connection will be closed, because of a disconnection
     * of another client
     */
    void updateForcedEndGame();

    void updateLeaderboardFromDB(int PlayerPositionInDB, List<LeaderBoardData> leaderboard);





}