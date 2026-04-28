package it.polimi.ingsw.Network.Socket.Moves;

import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Game.Player;
import it.polimi.ingsw.Network.Socket.ClientSocketProxy;

public class LoginCommand implements Command {
    private final String username;
    private final String totem;

    /**
     * @param gameManager
     * @param client
     */
    @Override
    public void execute(GameManager gameManager, ClientSocketProxy client) {
        synchronized (gameManager.getPlayers()) {
            gameManager.getPlayers().add(new Player(username, totem, 0));
        }
    }

    /**
     *
     */
    @Override
    public void execute() {

    }

    public LoginCommand(String username, String totem) {
        this.username = username;
        this.totem = totem;
    }
}
