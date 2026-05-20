package it.polimi.ingsw.Network.Socket.Client;

import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.ServerConnection;
import it.polimi.ingsw.Network.Socket.Client.Command.ClientCommand;
import it.polimi.ingsw.Network.Socket.Client.Command.CommandFactoryClientSide;
import it.polimi.ingsw.Network.Socket.Server.MessageFromServer;

import java.io.*;
import java.net.Socket;

public class SocketClient implements Runnable, ServerConnection {
    //private Socket socket;
    public static final Object outputLock = new Object();
    public ObjectOutputStream out;
    public final ClientController clientController;

    public SocketClient( ClientController clientController) {
        this.clientController = clientController;
        clientController.setConnection(this);
        run();
    }

    @Override
    public void run(){
        try {
            Socket socket = new Socket("localhost", 8000);
            System.out.println("SocketClient started...");
            synchronized (outputLock) {
                try {
                    out = new ObjectOutputStream(socket.getOutputStream());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            //implementazione heartbeat
            CommandFactoryClientSide commandFactory = new CommandFactoryClientSide();
            while(true){
                MessageFromServer msg = (MessageFromServer) inputStream.readObject();
                ClientCommand cmd = commandFactory.getCommand(msg);

                new Thread(()->{
                    try {
                        cmd.execute(socket, clientController);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendTotem(Totem totem){
        new Thread(()->{
            synchronized (outputLock){
                try {
                    out.writeObject(new MessageFromClient<>("totem",totem));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public void sendUsername(String userName){
        new Thread(()->{
            synchronized (outputLock){
                try {
                    out.writeObject(new MessageFromClient<>("username",userName));
                } catch (IOException e) {}
            }
        }).start();
    }

    @Override
    public void login(String username, Totem chosenTotem) {
            synchronized (outputLock){
                try {
                    out.writeObject(new MessageFromClient<>("username",username));
                    out.flush();
                } catch (IOException e) {}

                try {
                    out.writeObject(new MessageFromClient<>("totem", chosenTotem));
                    out.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

    }

    @Override
    public void setNumPlayers(int numPlayer) {

            synchronized (outputLock){
                try {
                    out.writeObject(new MessageFromClient<>("setnumplayers",numPlayer));
                    out.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

    }

    @Override
    public void requestPickCard(String localPlayerName, boolean isUpper, boolean isBuilding, int index) {

    }


    public void pickCard(String username, boolean isUpper, boolean isBuilding, int index) {
        new Thread(()->{
//            Pick pick = new Pick(username, isUpper, isBuilding, index);
//            synchronized (outputLock){
//                try {
//                    out.writeObject(new MessageFromClient<>("pick",pick));
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
        }).start();
    }

    @Override
    public void setTotemPosition(String localPlayerName, int chosenPosition) {

            synchronized (outputLock){
                try {
                    out.writeObject(new MessageFromClient<>("position", chosenPosition));
                    out.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

    }

    @Override
    public void leave() {

    }


    public void availableColorsRequest() {
        synchronized (outputLock){
            try {
                out.writeObject(new MessageFromClient<>("available_colors",null));
                out.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
