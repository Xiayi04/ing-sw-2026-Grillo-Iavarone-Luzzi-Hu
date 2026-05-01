package it.polimi.ingsw.Network;

import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Game.Player;

import java.io.IOException;

public interface ClientProxy {
    void askForLogin(String[] availableTotems);
    void askForNumPlayers();
    void askForRowBuildingIndex() throws IOException;
    void askForBuildingIndex(int row);
    void notifyAll(Player player, Event event);
}