package it.polimi.ingsw.Network;

import it.polimi.ingsw.Game.Totem;
// LATO CLIENT , VALIDA SIA PER RMI E SOCKET
//interfaccia DAL CLIENTCONTROLLER per mandare messaggi  al server
public interface ServerConnection {
    void login(String username, Totem chosenTotem) ;

    void setNumPlayers(int numPlayer) ;

   // void moveTotem(int index);

    void pickCard(String username, boolean isUpper, boolean isBuilding, int index) ;

    void setTotemPosition(int chosenPosition);

    void leave();  //per comunicare ad altri client che x si è disconnesso



}
