package it.polimi.ingsw.Network.Socket.Server;
import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.OfferCard;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.Socket.Server.Command.Pick;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
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
    public record TotemPosition(Player player,int index){};

    @Override
    public void returnedTotemOnTurnOrder(Player player,int index){
        synchronized (outputLock){
            try {
                out.writeObject(new MessageFromServer<>("turn_order_card_position", new TotemPosition(player,index) ));
                out.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void updateEra(int era){
        synchronized (outputLock){
            try {
                out.writeObject(new MessageFromServer<>("new_era", era));
                out.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void resolvingEvent(Event e){
        synchronized (outputLock){
            try {
                out.writeObject(new MessageFromServer<>("resolving_event", e));
                out.flush();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void confirmUsername(String username) {

            synchronized (outputLock) {
                try {
                    out.writeObject(new MessageFromServer<>("confirm_username", username));
                    out.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

    }

    @Override
    public void confirmTotem(Totem totem){

            synchronized (outputLock) {
                try {
                    out.writeObject(new MessageFromServer<>("confirm_totem", totem));
                    out.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

    }

    @Override
    public void setNumPlayersError(){

            synchronized (outputLock) {
                try {
                    out.writeObject(new MessageFromServer<>("setnumplayers_error", null));
                    out.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

    }

    @Override
    public void totemNotAvailableError(ArrayList<Totem> totems) {

            synchronized (outputLock) {
                try {
                    out.writeObject(new MessageFromServer<>("totem_error", totems));
                    out.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

    }


    @Override
    public void showMessage(String message) throws IOException {
        synchronized (outputLock) {
            out.writeObject(new MessageFromServer<>("MSG", message));
            out.flush();
        }
    }

    @Override
    public void showEndGame() throws RemoteException {

    }


    @Override
    public void showStartGame(String myName, ArrayList<Player> players, Totem myTotem, int myFood) throws RemoteException {

    }

    @Override
    public void askForTotemMove(ArrayList<OfferCard> path) throws RemoteException {

    }

    @Override
    public void askForLogin() throws IOException {

    }


    @Override
    public void askForLogin(List<Totem> availableTotems) throws RemoteException {

    }

    @Override
    public void askNumPlayers() throws IOException {
        synchronized (outputLock) {
            out.writeObject(new MessageFromServer<>("SETNUMPLAYERS", null));
            out.flush();
        }
    }

    @Override
    public void refuseConnection() throws IOException {
        synchronized (outputLock){
            out.writeObject(new MessageFromServer<>("REFUSECONNECTION", null));
            out.flush();
        }
    }

    @Override
    public void newPlayer(Player newPlayer) throws IOException {
        synchronized (outputLock){
            out.writeObject(new MessageFromServer<>("NEWPLAYER",newPlayer));
            out.flush();
        }
    }

    @Override
    public void pickedCard(Player player, boolean row, boolean isBuilding, int index) {
        synchronized (outputLock){
            try {
                out.writeObject(new MessageFromServer<>("picked_card", new Pick(player.getName(),row,isBuilding,index)));
                out.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void movedTotem(Player player, int index) {
        synchronized (outputLock){
            try {
                out.writeObject(new MessageFromServer<>("moved_totem", new TotemPosition(player,index)));
                out.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void updatePlayerFood(Player player, int update) {

    }

    @Override
    public void updatePlayerPP(Player player, int update) {

    }

    @Override
    public void showPlayerTurn(Player player) {

    }

    @Override
    public void cardPickError() {

    }

    @Override
    public void buildingPurchaseError() {

    }

    @Override
    public void totemPositionError() {

    }


    @Override
    public void showMyTurn() throws RemoteException {

    }

    @Override
    public void updateBoardStatus(Board board) throws RemoteException {

    }

    @Override
    public void showCurrentPlayer(String playerName) throws RemoteException {

    }

    @Override
    public void showError(String message) throws RemoteException {

    }

    @Override
    public void updateOtherPlayerStatus(String playerName, List<Card> tribeCards, List<Building> buildings) throws RemoteException {

    }

    @Override
    public void notifyAllPlayers(Player player, Event event) throws RemoteException {

    }


}
