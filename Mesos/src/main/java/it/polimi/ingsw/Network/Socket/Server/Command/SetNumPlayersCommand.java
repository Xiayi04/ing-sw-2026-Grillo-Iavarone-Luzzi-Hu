package it.polimi.ingsw.Network.Socket.Server.Command;

import it.polimi.ingsw.Controller.ServerController;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.util.Arrays;

public class SetNumPlayersCommand implements ServerCommand{
    private  int numPlayers;
    public SetNumPlayersCommand(Integer numPlayers) {
        this.numPlayers = numPlayers;
    }
    /**
     *
     */
    @Override
    public void execute(VirtualClientInterface client, ServerController serverController) {
        serverController.getLobby().setNumPlayers(numPlayers,client);
    }

}
