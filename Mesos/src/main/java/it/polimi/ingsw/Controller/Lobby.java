package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Network.VirtualClientInterface;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Lobby {
    private static final ArrayList<VirtualClientInterface> clients = new ArrayList<>();
    private final ServerController serverController;
    private final GameManager gameManager;
    public static final AtomicBoolean isNumPlayersSetted = new AtomicBoolean(false);

    public  Lobby(ServerController serverController){
        this.serverController = serverController;
        this.gameManager = serverController.getGM();
    }


    public synchronized void addClient(VirtualClientInterface client) throws RemoteException {
        synchronized (clients){
            //This should let the first player jump this 'if statement' even though the first condition is false
            synchronized (isNumPlayersSetted){
                if (isNumPlayersSetted.get() && clients.size() >= gameManager.getNumPlayers()) {
                    client.refuseConnection();
                    return;
                }
                isNumPlayersSetted.notify();
            }
            clients.add(client);

            if(clients.size()==1){
                client.askNumPlayers();
                checkSetNumPlayers();
                client.askForLogin();
            }

        }
    }

    public static ArrayList<VirtualClientInterface> getClients(){
        return clients;
    }


    public void checkSetNumPlayers(){
        new Thread(()->{
            int nPlayers = 0;
            synchronized (GameManager.numPlayersLock) {
                while(gameManager.getNumPlayers() <2){
                    try {
                        GameManager.numPlayersLock.wait();
                    } catch (InterruptedException e) {}
                }
                nPlayers = gameManager.getNumPlayers();
                GameManager.numPlayersLock.notifyAll();
            }

            synchronized (clients){

                for(int i = 1; i< nPlayers; i++){
                    try {
                        clients.get(i).askForLogin();
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }

                if(clients.size()>nPlayers){
                    for(int i = nPlayers; i< clients.size(); i++){
                        clients.get(i).refuseConnection();
                    }
                }

                synchronized (isNumPlayersSetted){
                    isNumPlayersSetted.set(true);
                    isNumPlayersSetted.notifyAll();
                }
                clients.notifyAll();
            }

        }).start();
    }
}
