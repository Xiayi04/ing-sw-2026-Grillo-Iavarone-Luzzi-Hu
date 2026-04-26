package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Game.Totem;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {
    void login(String username, Totem chosenColor, VirtualView client) throws RemoteException;

    void moveTotem(String username, int pathIndex) throws RemoteException;

    void pickCard(String username, boolean isUpper, boolean isBuilding, int index) throws RemoteException;
}
