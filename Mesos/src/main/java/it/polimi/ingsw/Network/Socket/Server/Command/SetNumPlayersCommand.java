package it.polimi.ingsw.Network.Socket.Server.Command;

import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Network.Socket.Server.ClientSocketProxy;

import java.util.Arrays;

public class SetNumPlayersCommand implements ServerCommand{
    private final String numPlayers;
    public SetNumPlayersCommand(String[] numPlayers) {
        this.numPlayers = Arrays.toString(numPlayers);
    }
    /**
     * @param gameManager: manages the game
     * @param client: proxy to the client
     */
    @Override
    public void execute(GameManager gameManager, ClientSocketProxy client) {

        synchronized (gameManager.numPlayersLock) {
            gameManager.setNumPlayers(Integer.parseInt(numPlayers));
        }
        //gameManager.numPlayersLock.notifyAll();
    }
}
