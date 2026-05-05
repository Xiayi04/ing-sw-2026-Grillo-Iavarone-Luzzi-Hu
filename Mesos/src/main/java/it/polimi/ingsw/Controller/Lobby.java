package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Network.VirtualClientInterface;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Lobby {
    private static final ArrayList<VirtualClientInterface> clients = new ArrayList<VirtualClientInterface>();
    private final ServerController serverController;
    private final GameManager gameManager;
    public static Boolean isNumPlayersSetted = false;

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
    public synchronized void addClient(VirtualClientInterface client) throws RemoteException {

        synchronized (clients){
            //This should let the first player jump this 'if statement' even though the first condition is false
            if(clients.size() >= gameManager.getNumPlayers() && isNumPlayersSetted ){
                //client.refuseConnection();
                return;
            }
            clients.add(client);

            if(clients.size()==1){

                synchronized (isNumPlayersSetted){

                    client.askNumPlayers();

                    while(!isNumPlayersSetted){

                        try {
                            sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println("ciao1");


                }


            }
            System.out.println("ciao2");
            //String totems = String.join(",", gameManager.getAvailableTotems());
            client.askForLogin();
        }
    }

    public static ArrayList<VirtualClientInterface> getClients(){
        return clients;
    }
}
