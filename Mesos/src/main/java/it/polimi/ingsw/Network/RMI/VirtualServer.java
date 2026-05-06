package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
// Il Client lo usa per mandare comandi verso il Server
public interface VirtualServer extends Remote {

    void connect(RemoteClientInterface client) throws RemoteException;

    void login(String username, Totem chosenColor, RemoteClientInterface  client) throws RemoteException;

    void moveTotem(String username, int pathIndex) throws RemoteException;

    void pickCard(String username, boolean isUpper, boolean isBuilding, int index) throws RemoteException;

    void setNumPlayers(int numPlayer) throws RemoteException;

    void setTotemPosition(int index) throws RemoteException;
}
