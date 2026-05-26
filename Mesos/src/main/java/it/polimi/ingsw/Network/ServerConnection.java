package it.polimi.ingsw.Network;

import it.polimi.ingsw.Game.Totem;

import java.util.ArrayList;

// LATO CLIENT , VALIDA SIA PER RMI E SOCKET
//interfaccia DAL CLIENTCONTROLLER per mandare messaggi  al server
public interface ServerConnection  extends AutoCloseable{
    void login(String username, Totem chosenTotem) ;

    void requestSetNumPlayers(int numPlayer) ;

    void requestPickCard(String localPlayerName, boolean isUpper, boolean isBuilding, int index) ;

    void requestMoveTotem(String localPlayerName, int chosenPosition);

    void requestAvailableTotems();

    void leave();

    @Override
    void close();
}
