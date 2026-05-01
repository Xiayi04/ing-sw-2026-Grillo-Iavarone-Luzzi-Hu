package it.polimi.ingsw.Network.Socket.Server.Command;

import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Network.Socket.Server.ClientSocketProxy;

public class LoginCommand implements ServerCommand {
    private final String username;
    private final String totem;

    /**
     * @param gameManager
     *
     */
    @Override
    public void execute(GameManager gameManager, ClientSocketProxy proxy) {
        gameManager.addPlayer(username,totem,proxy);
    }

    public LoginCommand(String username, String totem) {
        this.username = username;
        this.totem = totem;
    }
}
