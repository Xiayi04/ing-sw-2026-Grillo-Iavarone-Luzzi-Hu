package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.Socket.Server.Command.UpdatePP;

import java.net.Socket;

public class UpdatePPCommand implements ClientCommand {
    private final UpdatePP command;
    public UpdatePPCommand(UpdatePP command) {
        this.command = command;
    }

    @Override
    public void execute(Socket socket, ClientController clientController) {
        clientController.updatePlayerPP(command.playerName(), command.update());
    }
}
