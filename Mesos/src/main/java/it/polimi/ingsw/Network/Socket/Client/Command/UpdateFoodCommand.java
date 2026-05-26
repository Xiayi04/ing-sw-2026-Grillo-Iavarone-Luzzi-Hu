package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.Socket.Server.Command.UpdateFood;

import java.net.Socket;

public class UpdateFoodCommand implements ClientCommand{
    private final UpdateFood command;
    public UpdateFoodCommand(UpdateFood command) {
        this.command = command;
    }

    @Override
    public void execute(Socket socket, ClientController clientController) {
        clientController.foodUpdated(command.playerName(),  command.food());
    }
}
