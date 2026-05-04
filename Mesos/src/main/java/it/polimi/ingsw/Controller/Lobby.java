package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Network.VirtualClient;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Lobby {
    private static final ArrayList<VirtualClient> clients = new ArrayList<VirtualClient>();
    private final ServerController serverController;
    private final GameManager gameManager;
    private boolean isNumPlayersSetted = false;

    public  Lobby(ServerController serverController){
        this.serverController = serverController;
        this.gameManager = serverController.getGM();
    }


    /**
     * This method must be called when a client asks to connect to the server. It adds the client's
     * VirtualClient to a list, if he's the first starts a procedure to ask him the number of players that
     * will join the game, while this happens no other client can be accepted by the server in fact
     * there is a while that waits for modify in GameManager.numPlayers
     *
     */
    public synchronized void addClient(VirtualClient client) throws RemoteException {

        synchronized (clients){
            //This should let the first player jump this 'if statement' even though the first condition is false
            if(clients.size() >= gameManager.getNumPlayers() || !isNumPlayersSetted ){
                client.refuseConnection();
                return;
            }
            clients.add(client);

            if(clients.size()==1){

                synchronized (GameManager.numPlayersLock){
                    int precNumPlayers = gameManager.getNumPlayers();
                    client.askNumPlayers();

                    while(precNumPlayers == gameManager.getNumPlayers()){

                        try {
                            GameManager.numPlayersLock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    GameManager.numPlayersLock.notifyAll();
                    this.isNumPlayersSetted = true;

                }

                return;
            }
            //notify other players that a new player joined the game
        }
    }

    public static ArrayList<VirtualClient> getClients(){
        return clients;
    }
}
