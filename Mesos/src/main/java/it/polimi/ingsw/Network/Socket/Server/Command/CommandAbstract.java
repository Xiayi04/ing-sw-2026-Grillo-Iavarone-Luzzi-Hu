package it.polimi.ingsw.Network.Socket.Server.Command;

import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Network.Socket.Server.ClientSocketProxy;

public abstract class CommandAbstract implements  ServerCommand {
    @Override
    public void execute(GameManager gameManager, ClientSocketProxy client){}

}
