package it.polimi.ingsw.Network.Socket.Client;

import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.ClientMain;
import it.polimi.ingsw.Network.ServerConnection;
import it.polimi.ingsw.Network.Socket.Client.Command.ClientCommand;
import it.polimi.ingsw.Network.Socket.Client.Command.CommandFactoryClientSide;
import it.polimi.ingsw.Network.Socket.Server.Command.Pick;
import it.polimi.ingsw.Network.Socket.Server.Command.TotemPosition;
import it.polimi.ingsw.Network.Socket.Server.MessageFromServer;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketClient implements Runnable, ServerConnection, AutoCloseable{
    //private Socket socket;
    public  final Object outputLock = new Object();
    public ObjectOutputStream out;
    public final ClientController clientController;
    private volatile boolean closeSignal = false;
    private ObjectInputStream in;
    private Socket socket;
    private final String serverIP;
    private final int socketPort = 8000;
    private final ExecutorService pool = Executors.newCachedThreadPool();

    public SocketClient( ClientController clientController, String serverIP) {
        this.serverIP = serverIP;
        this.clientController = clientController;
        new Thread(this).start();
    }

    @Override
    public void run(){
        try {
            socket = new Socket(serverIP, socketPort);
            //System.out.println("SocketClient started...");
            synchronized (outputLock) {
                try {
                    out = new ObjectOutputStream(socket.getOutputStream());
                } catch (SocketException e){
                    clientController.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            in = new ObjectInputStream(socket.getInputStream());

            //implementazione heartbeat
            CommandFactoryClientSide commandFactory = new CommandFactoryClientSide();

            while(!closeSignal){
                MessageFromServer msg = null;
                try {
                    msg = (MessageFromServer) in.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    clientController.handleForcedEndGame();
                    return;
                }
                ClientCommand cmd = commandFactory.getCommand(msg);

                pool.submit(()-> cmd.execute(socket, clientController));
            }

        } catch (Exception e) {
            System.out.println("Connection failed");
            ClientMain.terminateClient();
        }
    }

    public <T> void send (String command, Object payload){
        synchronized (outputLock) {
            try{
                out.writeObject(new MessageFromClient<>(command, payload));
                out.flush();
            }catch (IOException e){
                clientController.handleForcedEndGame();
            }
        }
    }


    /**
     * Checks if the closing procedure was already started checking closeSignal
     * Closes the output and input related to the socket and then closes the socket itself
     * The exceptions are ignored because if they have been thrown it means the
     * socket was already closed by another procedure
     */
    @Override
    public synchronized void close(){
        if(closeSignal){
            return;
        }
        closeSignal = true;
        if(in!=null){
            try {
                in.close();
            } catch (IOException ignored){}
        }
        if(out!=null){
            try {
                out.close();
            } catch (IOException ignored) {}
        }
        if(socket!=null && !socket.isClosed()){
            try {
                socket.close();
            } catch (IOException ignored) {}
        }

    }

    public void sendTotem(Totem totem){
        send("totem",totem);
    }

    @Override
    public void login(String username, Totem chosenTotem) {
        send("username",username);
        send("totem", chosenTotem);
    }

    @Override
    public void requestSetNumPlayers(int numPlayer) {
        send("setnumplayers",numPlayer);
    }

    @Override
    public void requestPickCard(String localPlayerName, boolean isUpper, boolean isBuilding, int index, boolean skip) {
        send("pick", new Pick(localPlayerName, isUpper, isBuilding, index, skip,0));
    }

    @Override
    public void requestMoveTotem(String localPlayerName, int chosenPosition) {
        send("position", new TotemPosition(localPlayerName, chosenPosition));
    }

    @Override
    public void leave() {
        send("quit", null);
    }

    @Override
    public void requestAvailableTotems() {
        send("available_colors",null);
    }
}
