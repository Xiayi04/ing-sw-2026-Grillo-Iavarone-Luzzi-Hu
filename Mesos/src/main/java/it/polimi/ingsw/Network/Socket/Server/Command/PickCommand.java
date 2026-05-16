package it.polimi.ingsw.Network.Socket.Server.Command;

import it.polimi.ingsw.Controller.ServerController;
import it.polimi.ingsw.Network.VirtualClientInterface;

public class PickCommand implements ServerCommand{
    private Pick pick;

    public PickCommand(Pick pick){
        this.pick = pick;
    }

    @Override
    public void execute(VirtualClientInterface client, ServerController serverController) {
        new Thread(()->{
            serverController.genericPick(pick.username(), pick.isUpper(), pick.isBuilding(), pick.index());
        }).start();
    }


}
