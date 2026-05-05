package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.ClientInterface;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.rmi.RemoteException;

public class ClientRMI implements ClientInterface {

    private final VirtualServer server;
    private final VirtualClientInterface virtualClient;

    public ClientRMI(VirtualServer server, VirtualClientInterface virtualClient) {
        this.server = server;
        this.virtualClient = virtualClient;
    }

    public void login(String username, Totem chosenTotem) {
        try {
            server.login(username, chosenTotem, virtualClient);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setNumPlayers(int numPlayers) {
        try {
            server.setNumPlayers(numPlayers);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    public void moveTotem(String username, int pathIndex){
        try {
            server.moveTotem(username, pathIndex);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    public void pickCard(String username, boolean isUpper, boolean isBuilding, int index){
        try {
            server.pickCard(username, isUpper,isBuilding,index);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }


    public void setTotemPosition(int chosenPosition){
        try {
            server.setTotemPosition(chosenPosition);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    public void leave(VirtualClientInterface client){
        try {
            server.leave(client);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


}