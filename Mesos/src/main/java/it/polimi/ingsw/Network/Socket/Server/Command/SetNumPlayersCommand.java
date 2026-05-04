package it.polimi.ingsw.Network.Socket.Server.Command;

import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Controller.ServerController;
import it.polimi.ingsw.Network.VirtualClient;

import java.util.Arrays;

public class SetNumPlayersCommand implements ServerCommand{
    private final String numPlayers;
    public SetNumPlayersCommand(String[] numPlayers) {
        this.numPlayers = Arrays.toString(numPlayers);
    }
    /**
     *
     */
    @Override
    public void execute(GameManager gameManager, VirtualClient client, ServerController serverController) {
        serverController.setNumPlayers(Integer.parseInt(numPlayers));
    }

}
