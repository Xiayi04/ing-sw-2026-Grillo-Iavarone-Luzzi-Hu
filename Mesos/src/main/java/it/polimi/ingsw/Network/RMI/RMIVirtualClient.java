package it.polimi.ingsw.Network.RMI;


import it.polimi.ingsw.Model.Cards.Events.Event;
import it.polimi.ingsw.Database.LeaderBoardData;
import it.polimi.ingsw.Model.Game.Board;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Totem;
import it.polimi.ingsw.Network.ClientDisconnectedException;

import it.polimi.ingsw.Network.PlayerScore;
import it.polimi.ingsw.Network.VirtualClientInterface;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class RMIVirtualClient implements VirtualClientInterface {
//riceve le richieste dal server per notificare il client
    //serve al server per notificare il client
    private final RemoteClientInterface client;

    public RMIVirtualClient(RemoteClientInterface client) {
        this.client = client;
    }

    @Override
    public void skipTurn() {

    }

    @Override
    public void showUpdateEra(int era)  {
        try{
            client.showLocalUpdateEra(era);
        } catch (RemoteException e) {
            throw new ClientDisconnectedException(e.getMessage());
        }
    }

    @Override
    public void updateForEvent(Event e) {
        try{
            client.showUpdateForEvent(e);
        }catch(RemoteException d){
            throw new ClientDisconnectedException (d.getMessage());
        }
    }

    @Override
    public void returnTotemToTOC(String username, int index)  {
        try{
            client.showLocalReturnToTOC(username,index);
        }catch(RemoteException e){
            throw new ClientDisconnectedException(e.getMessage());
        }

    }

    @Override
    public void showChosenNumPlayers(int numPlayers)  {
        try {
            client.showChosenNumPlayers(numPlayers);
        }catch(RemoteException e){
            throw new ClientDisconnectedException(e.getMessage());
        }

    }

    @Override
    public void updateFirstPlayer() {
        try{
            client.showUpdateFirstPlayer();
        }catch(RemoteException e) {
            throw new ClientDisconnectedException( e.getMessage());
        }
    }


    @Override
    public void ping() {
        try{
            client.ping();
        }catch(RemoteException e){
            throw new ClientDisconnectedException(e.getMessage());
        }
    }

    @Override
    public void updateStartGame(ArrayList<Player> players, Board board) {
        try{
            client.showGameStarted(players,board);
        } catch (RemoteException e) {
            throw new ClientDisconnectedException(e.getMessage());
        }
    }


    @Override
    public void showPlayerTurn(String username) {
        try {
            client.showPlayerTurn(username);
        } catch (RemoteException e) {
            throw new ClientDisconnectedException(e.getMessage());
        }
    }

    @Override
    public void updateNextRound(Board board, int round)  {
        try{
            client.updateNextTurn(board, round);
        }catch(RemoteException e){
            throw new ClientDisconnectedException(e.getMessage());
        }

    }


    @Override
    public void pickedCard (String username, boolean row, boolean isBuilding, int index, int round) {
        try {
            client.showPickedCard(username, row, isBuilding, index, round);
        } catch (RemoteException e) {
            throw new ClientDisconnectedException(e.getMessage());
        }
    }
    @Override
    public void movedTotem(String username, int index){
        try{
            client.showTotemMoved(username, index);
        } catch (RemoteException e) {
            throw new ClientDisconnectedException(e.getMessage());
        }
    }

    @Override
    public void movedTotemError()  {
        try{
            client.showTotemMovedError("MOVED TO THE TOTEM ERROR");
        } catch (RemoteException e) {
            throw new ClientDisconnectedException(e.getMessage());
        }

    }

    @Override
    public void totemChoiceError()  {
        try{
            client.showTotemChoiceError("ERRORE");
        } catch (RemoteException e) {
            throw new ClientDisconnectedException(e.getMessage());
        }

    }

    @Override
    public void updatePlayerFood(String username, int foodUpdated){
        try{
            client.showUpdatePlayerFood(username, foodUpdated);
        } catch (RemoteException e) {
            throw new ClientDisconnectedException(e.getMessage());
        }
    }
    @Override
    public void updatePlayerPP(String username, int PPUpdate){
        try{
            client.showUpdatePlayerPP(username,  PPUpdate );
        } catch (RemoteException e) {
            throw new ClientDisconnectedException(e.getMessage());
        }
    }
    @Override
    public void pickCardError(){
        try{
            client.showPickCardError("ERROR");
        } catch (RemoteException e) {
            throw new ClientDisconnectedException(e.getMessage());
        }
    }
    @Override
    public void skipError(){
        try{
            client.showSkipError("errore");
        }catch(RemoteException e){
            throw new ClientDisconnectedException(e.getMessage());
        }
    }


    @Override
    public void usernameError()  {
        try{
            client.showUsernameError("username error");
        }catch(RemoteException e){
            throw new ClientDisconnectedException(e.getMessage());
        }

    }


    @Override
    public void updateAvailableTotems(ArrayList<Totem> availableTotems) {
        try{
            client.showAvailableTotems(availableTotems);
        }catch(RemoteException e){
            throw new ClientDisconnectedException(e.getMessage());
        }

    }

    @Override
    public void numPlayersError()  {
        try{
            client.numPlayersChosenError("Number of players not valid");
        }catch(RemoteException e){
            throw new ClientDisconnectedException(e.getMessage());
        }

    }

    @Override
    public void updateConfirmedUsername(String username) {
        try{
            client.showConfirmUsername(username);
        }catch(RemoteException e){
            throw new ClientDisconnectedException(e.getMessage());
        }

    }

    @Override
    public void updateConfirmedTotem(Totem totem) {
        try{
            client.showConfirmTotem(totem);
        }catch(RemoteException e){
            throw new ClientDisconnectedException(e.getMessage());
        }

    }


    @Override
    public void updateEndGame(String winner, List<PlayerScore> leaderboard) {
        try {
            client.showEndGameSuccessfully(winner,leaderboard);
        } catch (RemoteException e) {
            throw new ClientDisconnectedException(e.getMessage());
        }
    }

    @Override
    public void refuseConnection() {
        try{
            client.refuseConnection();
        }catch(RemoteException e ){
            throw new ClientDisconnectedException(e.getMessage());
        }
    }

    @Override
    public void updateForcedEndGame() {
        try{
            client.showForcedEndGame();
        }catch (RemoteException e){
            throw new ClientDisconnectedException(e.getMessage());
        }
    }

    @Override
    public void updateLeaderboardFromDB(int playerPositionInDB, List<LeaderBoardData> leaderboard) {
        try{
            client.showLeaderboardFromDB(playerPositionInDB,leaderboard);
        }catch(RemoteException e ){
            throw new ClientDisconnectedException(e.getMessage());
        }
    }


}
//







