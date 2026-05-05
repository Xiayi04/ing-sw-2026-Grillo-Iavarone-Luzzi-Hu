package it.polimi.ingsw.Network;

import it.polimi.ingsw.Game.Totem;

import java.rmi.RemoteException;

//interfaccia per mandare messaggi da client al server
public interface ClientInterface {
    void login(String username, Totem chosenTotem) ;

    void setNumPlayers(int numPlayer) ;

    void moveTotem(String username, int pathIndex);

    void pickCard(String username, boolean isUpper, boolean isBuilding, int index) ;

    void setTotemPosition(int chosenPosition);

    void leave(VirtualClientInterface client);  //per comunicare ad altri client che x si è disconnesso



}
