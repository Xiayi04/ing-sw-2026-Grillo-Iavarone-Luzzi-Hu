package it.polimi.ingsw.Network;

import it.polimi.ingsw.Game.Totem;
// mi serve per mandare richieste al server
//interfaccia per mandare messaggi da client al server
public interface ServerConnection {
    void login(String username, Totem chosenTotem) ;

    void setNumPlayers(int numPlayer) ;

    void moveTotem(int index);

    void pickCard(boolean isUpper, boolean isBuilding, int index) ;

    //void setTotemPosition(int chosenPosition);

    void leave();  //per comunicare ad altri client che x si è disconnesso



}
