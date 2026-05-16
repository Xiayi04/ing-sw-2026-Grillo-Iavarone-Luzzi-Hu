package it.polimi.ingsw.Network.Socket.Server.Command;

import it.polimi.ingsw.Controller.ServerController;
import it.polimi.ingsw.Game.Totem;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.util.Arrays;

public class SetTotemCommand implements ServerCommand{
    Totem totem;
    public SetTotemCommand(Totem totem){
        this.totem = totem;
    }

    @Override
    public void execute(VirtualClientInterface client, ServerController serverController) {
        serverController.getLobby().addTotem(totem, client);
    }
}
