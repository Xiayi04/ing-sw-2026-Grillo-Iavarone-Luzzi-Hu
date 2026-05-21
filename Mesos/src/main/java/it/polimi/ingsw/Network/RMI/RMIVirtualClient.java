package it.polimi.ingsw.Network.RMI;


import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.PlayerScore;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.io.IOException;
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
    public void showUpdateEra(int era) throws RemoteException {
        try{
            client.showLocalUpdateEra(era);
        } catch (RemoteException e) {
            System.err.println("Error in showUpdateEra: " + e.getMessage());
        }
    }

    @Override
    public void updateForEvent(Event e) throws RemoteException {
        try{
            client.showUpdateForEvent(e);
        }catch(RemoteException d){
            System.err.println("Error in updateForEvent: " + d.getMessage());
        }
    }

    @Override
    public void returnTotemToTOC(String username, int index) throws RemoteException {
        try{
            client.showLocalReturnToTOC(username,index);
        }catch(RemoteException e){
            System.err.println("Error in returnTotemToTOC: " + e.getMessage());
        }

    }

    @Override
    public void showChosenNumPlayers(int numPlayers) throws IOException {
        try {
            client.showChosenNumPlayers(numPlayers);
        }catch(RemoteException e){
            System.err.println("Error in showChosenNumPlayers: " + e.getMessage());
        }

    }

    @Override
    public void updateFirstPlayer(String username) throws RemoteException {
        try{
            client.showUpdateFirstPlayer(username);
        }catch(RemoteException e) {
            System.err.println("Error in updateFirstPlayer: " + e.getMessage());
        }
    }

    @Override
    public void setNumPlayers(int numPlayers) throws RemoteException {
            /*try{
               // client.setNumPlayers(numPlayers);
            }catch (RemoteException e){
                System.err.println("Error in setNumPlayers: " + e.getMessage());
            }*/
        }

    @Override
    public void ping() throws RemoteException {
        client.ping();
    }

    @Override
    public void updateStartGame(ArrayList<Player> players, Board board) throws RemoteException {
        try{
            client.showGameStarted(players,board);
        } catch (RemoteException e) {
            System.out.println("Error while sending start game notification");
        }
    }


    /*@Override
    public void showMessage(String message) throws RemoteException, IOException {
        try{
           client.showErrorMessage(message);
        } catch (RemoteException e) {
            System.err.println("Error in showMessage: " + e.getMessage());
        }
    }*/

    @Override
    public void showPlayerTurn(String player) {
        try {
            client.showPlayerTurn(player);
        } catch (RemoteException e) {
            System.out.println(" ");
        }
    }

    @Override
    public void updateNextTurn(Board board) throws RemoteException {
        try{
            client.updateNextTurn(board);
        }catch(RemoteException e){
            System.err.println("Error in updateNextTurn: " + e.getMessage());
        }

    }

    @Override
    public void pickedCard (String username, boolean row, boolean isBuilding, int index) {
        try {
            client.showPickedCard(username, row, isBuilding, index);
        } catch (RemoteException e) {
            System.out.println(username + " picked a card ");
        }
    }
    @Override
    public void movedTotem(String username, int index){
        try{
            client.showTotemMoved(username, index);
        } catch (RemoteException e) {
            System.out.println(username + "'s totem moved to: " + index );
        }
    }

    @Override
    public void movedTotemError() throws RemoteException {
        try{
            client.showTotemMovedError("MOVED TO THE TOTEM ERROR");
        } catch (RemoteException e) {
            System.err.println("Error in movedTotemError: " + e.getMessage());
        }

    }

    @Override
    public void totemChoiceError() throws RemoteException {
        try{
            client.showTotemChoiceError("ERRORE");
        } catch (RemoteException e) {
            System.err.println("Error in totemChoiceError: " + e.getMessage());
        }

    }

    @Override
    public void updatePlayerFood(String username, int foodUpdated){
        try{
            client.showUpdatePlayerFood(username, foodUpdated);
        } catch (RemoteException e) {
            System.out.println(username +"'s food updated: "+ foodUpdated);
        }
    }
    @Override
    public void updatePlayerPP(String username, int PPUpdate){
        try{
            client.showUpdatePlayerPP(username,  PPUpdate );
        } catch (RemoteException e) {
            System.out.println(username +"'s pp updated: "+  PPUpdate);
        }
    }
    @Override
    public void pickCardError(){
        try{
            client.showPickCardError("ERROR");
        } catch (RemoteException e) {
            System.out.println("error card chosen not valid");
        }
    }
    @Override
    public void buildingPurchaseError(){
        try{
            client.showPickCardError("Building not purchasable ");
        } catch (RemoteException e) {
            System.out.println("error: building not purchasable ");
        }
    }
    @Override
    public void totemPositionError(){
        try{
            client.showTotemMovedError("Position not valid or already chosen");
        } catch (RemoteException e) {
            System.out.println("error:not valid or already chosen: ");
        }
    }

    @Override
    public void usernameError() throws RemoteException {
        try{
            client.showUsernameError("ERRore USERNAME");
        }catch(RemoteException e){
            System.err.println("Error in usernameError: " + e.getMessage());
        }

    }

    @Override
    public void totemNotAvailableError(ArrayList<Totem> availableTotems) {
        try{
            client.showTotemMovedError("totem error");
        } catch (RemoteException e) {
            System.err.println("Error in totemNotAvailableError: " + e.getMessage());
        }

    }

    @Override
    public void updateAvailableTotems(ArrayList<Totem> availableTotems) {
        try{
            client.showAvailableTotems(availableTotems);
        }catch(RemoteException e){
            System.err.println("Error in updateAvailableTotems: " + e.getMessage());
        }

    }

    @Override
    public void numPlayersError() throws RemoteException {
        try{
            client.numPlayersChosenError("Number of players not valid");
        }catch(RemoteException e){
            System.err.println("Error in numPlayersError: " + e.getMessage());
        }

    }

    @Override
    public void updateConfirmedUsername(String username) {
        try{
            client.showConfirmUsername(username);
        }catch(RemoteException e){
            System.err.println("Error in updateConfirmedUsername: " + e.getMessage());
        }

    }

    @Override
    public void updateConfirmedTotem(Totem totem) {
        try{
            client.showConfirmTotem(totem);
        }catch(RemoteException e){
            System.err.println("Error in updateConfirmedTotem: " + e.getMessage());
        }

    }

    @Override
    public void confirmNumPlayers(int numPlayers) {

    }

    @Override
    public void availableColors(ArrayList<Totem> availableTotems) {

    }

    @Override
    public void showEndGame(String winner, List<PlayerScore> leaderboard) {
        try {
            client.showEndGame(winner,leaderboard);
        } catch (RemoteException e) {
            System.out.println(" ");
        }
    }

    @Override
    public void refuseConnection() throws IOException {
        /*try{
            client.co
        }catch(RemoteException e ){
            System.err.println("Error in refuseConnection: " + e.getMessage());
        }*/
    }

}
//







