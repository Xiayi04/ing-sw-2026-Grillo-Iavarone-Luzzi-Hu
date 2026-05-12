package it.polimi.ingsw.Network.Socket.Server;
import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.OfferCard;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class SocketVirtualClient implements VirtualClientInterface {
    private final Socket socket;
    private final PrintWriter out;
    //private final BufferedReader in;
    private final Object outputLock = new Object();
    private final char separator = '#';
    private final GameManager gm;

    public SocketVirtualClient(Socket socket, GameManager gameManager) throws IOException {
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream(), true );
        //this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.gm = gameManager;
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

    @Override
    public void showMessage(String message) throws RemoteException {
        synchronized (outputLock) {
            out.println("MSG"+separator+message);
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
    public void askForLogin(List<Totem> availableTotems) throws RemoteException {

    }

    @Override
    public void askNumPlayers() throws RemoteException {
        synchronized (outputLock) {
            out.println("SETNUMPLAYERS"+separator);
        }
    }



    @Override
    public void askForLogin() throws RemoteException {

        //trovare totem disponibili
        synchronized (outputLock){
            out.println("LOGIN"+separator);
        }
    }

    @Override
    public void refuseConnection(){
        synchronized (outputLock){
            out.println("REFUSECONNECTION"+separator);
        }
    }

    @Override
    public void newPlayer(Player newPlayer) throws RemoteException {
        synchronized (outputLock){
            out.println("NEWPLAYER"+separator+newPlayer.getName() + "," +  newPlayer.getTotem().toString());
        }
    }

    @Override
    public void pickedCard(Player player, boolean row, boolean isBuilding, int index) {

    }

    @Override
    public void movedTotem(Player player, int index) {

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


}
