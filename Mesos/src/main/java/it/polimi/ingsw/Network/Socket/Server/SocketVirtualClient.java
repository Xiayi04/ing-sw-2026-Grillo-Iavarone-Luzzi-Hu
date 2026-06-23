package it.polimi.ingsw.Network.Socket.Server;

import it.polimi.ingsw.Model.Cards.Events.Event;
import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Database.LeaderBoardData;
import it.polimi.ingsw.Model.Game.Board;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Totem;
import it.polimi.ingsw.Network.ClientDisconnectedException;
import it.polimi.ingsw.Network.PlayerScore;
import it.polimi.ingsw.Network.Socket.Server.Command.*;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class SocketVirtualClient implements VirtualClientInterface {
    private final Socket socket;
    private final ObjectOutputStream out;
    //private final BufferedReader in;
    private final Object outputLock = new Object();
    private final GameManager gm;

    public SocketVirtualClient(Socket socket, GameManager gameManager) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        //this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.gm = gameManager;
    }


    private <T> void send(String commandType, T data){
        synchronized (outputLock) {
            try {
                out.reset();
                out.writeObject(new MessageFromServer<>(commandType, data));
                out.flush();
            } catch (SocketException e) {
                throw new ClientDisconnectedException("Client network connection lost during command: " + commandType);
            } catch (IOException e) {
                System.out.println("Generic Error while sending " +  commandType + ": " + e.getMessage());
            }
        }
    }

    //public record TotemPosition(String playerName, int index) implements Serializable {};


    @Override
    public void skipTurn() {
        send("skip", null);
    }

    @Override
    public void updateForcedEndGame() {
        send("close_connection", null);
    }

    @Override
    public void ping() {
        send("ping", null);
    }

    @Override
    public void showChosenNumPlayers(int numPlayers){
        send("chosen_num_players", numPlayers);
    }

    @Override
    public void updateEndGame(String winner, List<PlayerScore> leaderboard) {
        send("end_game", new EndGameData(winner, leaderboard));
    }

    @Override
    public void movedTotemError(){
        send("moved_totem_error", null);
    }

    @Override
    public void totemChoiceError() {
        send("totem_error", null);
    }

    @Override
    public void returnTotemToTOC(String playerName, int index){
        send("turn_order_card_position", new TotemPosition(playerName, index));
    }

    @Override
    public void showUpdateEra(int era) {
        send("update_era", era);
    }

    @Override
    public void updateForEvent(Event e){
        send("resolving_event", e);
    }

    @Override
    public void updateConfirmedUsername(String username) {
        send("confirm_username", username);
    }

    @Override
    public void updateConfirmedTotem(Totem totem) {
        send("confirm_totem", totem);
    }

    public void confirmNumPlayers(int numPlayers) {
        send("confirm_numplayers", numPlayers);
    }

    @Override
    public void numPlayersError() {
        send("setnumplayers_error", null);
    }


    public record GameStartData(ArrayList<Player> players, Board board) implements Serializable {}

    @Override
    public void updateStartGame(ArrayList<Player> players, Board board)  {
        send("game_started", new GameStartData(players, board));
    }

    @Override
    public void updateFirstPlayer()  {
        send("setnumplayers", null);
    }

    @Override
    public void usernameError() {
        send("username_error", null);
    }

    @Override
    public void updateAvailableTotems(ArrayList<Totem> availableTotems) {
        send("colors", availableTotems);
    }

    @Override
    public void refuseConnection(){
        send("refuse_connection", null);
    }

    @Override
    public void pickedCard(String username, boolean row, boolean isBuilding, int index, int round) {
        send("picked_card", new Pick(username, row, isBuilding, index, false, round));
    }

    @Override
    public void movedTotem(String username, int index) {
        send("moved_totem", new TotemPosition(username, index));
    }

    @Override
    public void updatePlayerFood(String username, int update) {
        send("update_food", new UpdateFood(username, update));
    }

    @Override
    public void updatePlayerPP(String username, int update) {
        send("update_pp", new UpdatePP(username, update));
    }

    @Override
    public void showPlayerTurn(String username) {
        send("player_turn", username);
    }

    @Override
    public void updateNextRound(Board board, int round){
        send("next_round", new NextRoundData(board, round));
    }

    @Override
    public void pickCardError(){
        send("pick_error", null);
    }

    @Override
    public void skipError() {
        send("skip_error",null);
    }

    @Override
    public void updateLeaderboardFromDB(int PlayerPositionInDB, List<LeaderBoardData> leaderboard) {
        send("db_leaderboard", new DBData(PlayerPositionInDB,leaderboard));
    }
}
