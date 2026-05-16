package it.polimi.ingsw.Network.Socket.Server.Command;

import it.polimi.ingsw.Controller.ServerController;
import it.polimi.ingsw.Network.VirtualClientInterface;

import java.rmi.RemoteException;

public class MoveTotemCommand implements ServerCommand {
    private int position;
    public MoveTotemCommand(int position){
        this.position = position;
    }
    @Override
    public void execute(VirtualClientInterface client, ServerController serverController) {
        new Thread(()->{
            String username = serverController.getUsernameByClient(client);
            try {
                serverController.moveTotemRequest(username,position);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
