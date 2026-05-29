package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

// Il Client lo usa per mandare comandi verso il Server
public interface VirtualServer extends Remote {

    void connect(RemoteClientInterface virtualclient) throws RemoteException;

    void login(String username, Totem chosenColor, RemoteClientInterface  client) throws RemoteException;

    void requestMoveTotemManagement(String username, int pathIndex) throws RemoteException;

    void requestPickCardManagement(String username, boolean isUpper, boolean isBuilding, int index) throws RemoteException;

    void requestSetNumPlayersManagement(int numPlayer, RemoteClientInterface client ) throws RemoteException;

    void leave(RemoteClientInterface client) throws RemoteException;

    void requestAvailableTotemsManagement(String player, ArrayList<Totem> availabletotems) throws RemoteException;


}
