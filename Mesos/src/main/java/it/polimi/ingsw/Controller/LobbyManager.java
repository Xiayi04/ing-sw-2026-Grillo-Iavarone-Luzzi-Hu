package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.VirtualClientInterface;

public interface LobbyManager {
    void checkUsername(String username, VirtualClientInterface clientInterface);
    void checkTotem(Totem totem, VirtualClientInterface client);
    void checkSetNumPlayers(int numPlayers, VirtualClientInterface client);
    void connectionInitializer(VirtualClientInterface clientInterface);
}
