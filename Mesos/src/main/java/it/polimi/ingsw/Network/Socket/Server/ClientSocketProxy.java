package it.polimi.ingsw.Network.Socket.Server;
import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Network.ClientProxy;

import java.io.*;
import java.net.Socket;

public class ClientSocketProxy implements ClientProxy {
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


    /**
     *
     */
    @Override
    public void askForLogin(String[] totems) {
        String payload = String.join(",", totems);
        synchronized (GameManager.playersLock){
            int size = gm.getPlayers().size();
            synchronized (outputLock){
                out.println("LOGIN"+separator+payload);
            }
            while(true){

            }
        }

    }

    /**
     *
     */
    @Override
    public void askForNumPlayers() {
//Da inserire metodi da TUI

        synchronized (outputLock) {
            out.println("NUMPLAYERS"+separator);
        }
    }

    @Override
    public void askForRowBuildingIndex() throws IOException {
        synchronized (outputLock) {
            out.println("PICK"+separator);
        }
    }

    @Override
    public void askForBuildingIndex(int row){
        synchronized (outputLock) {
            out.println("PICK"+separator+row);
        }
    }

    /**
     * @param player
     * @param event
     */
    @Override
    public void notifyAll(Player player, Event event) {
        synchronized (outputLock){

        }

    }
}
