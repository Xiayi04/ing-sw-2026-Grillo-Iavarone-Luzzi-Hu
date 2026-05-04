package it.polimi.ingsw.Network.Socket.Server;
import it.polimi.ingsw.Buildings.Building;
import it.polimi.ingsw.Cards.Card;
import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Game.Board;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.ClientProxy;
import it.polimi.ingsw.Network.VirtualClient;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ClientSocketProxy implements VirtualClient {
    private final Socket socket;
    private final PrintWriter out;
    //private final BufferedReader in;
    private final Object outputLock = new Object();
    private final char separator = '#';
    private final GameManager gm;

    public ClientSocketProxy(Socket socket, GameManager gameManager) throws IOException {
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream(), true );
        //this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.gm = gameManager;
    }

    @Override
    public void updateBoardtatus(Board board) throws RemoteException {

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

    }

    @Override
    public void showEndGame(int finalScore) throws RemoteException {

    }

    @Override
    public void showStartGame(String myName, ArrayList<Player> players, Totem myTotem, int myFood) throws RemoteException {

    }

    @Override
    public void askNumPlayers() throws RemoteException {
        synchronized (outputLock) {
            out.println("SETNUMPLAYERS"+separator);
        }
    }

    @Override
    public void showStartGame() throws RemoteException {

    }

    @Override
    public void askForLogin() throws RemoteException {

        //trovare totem disponibili
        synchronized (outputLock){
            out.println("LOGIN"+separator+payload);
        }
    }

    @Override
    public void askForBuildingIndex(int row){
        synchronized (outputLock) {
            out.println("PICK"+separator+row);
        }
    }

    @Override
    public void askForTotemMove() throws RemoteException {

    }

    @Override
    public void showMyTurn() throws RemoteException {

    }

    @Override
    public void askForRowBuildingIndex() throws RemoteException, IOException {

    }


}
