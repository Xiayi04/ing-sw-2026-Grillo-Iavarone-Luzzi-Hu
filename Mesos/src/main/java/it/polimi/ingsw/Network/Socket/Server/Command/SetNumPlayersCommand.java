package it.polimi.ingsw.Network.Socket.Server.Command;

import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Controller.ServerController;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.util.Arrays;

public class SetNumPlayersCommand implements ServerCommand{
    private  String numPlayers;
    public SetNumPlayersCommand(String[] numPlayers) {
        this.numPlayers = Arrays.toString(numPlayers);
    }
    /**
     *
     */
    @Override
    public void execute(GameManager gameManager, VirtualClientInterface client, ServerController serverController) {
        numPlayers = numPlayers.replaceAll(" ","");
        numPlayers = numPlayers.trim();
        //numPlayers = numPlayers.replaceAll("[","");
        numPlayers = numPlayers.replaceAll("[^0-9]","");
        serverController.setNumPlayers(Integer.parseInt(numPlayers));
    }

}
