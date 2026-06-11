package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Network.ClientController;

import java.net.Socket;

public class ResolvingEventCommand implements ClientCommand {
    Event e ;
    public ResolvingEventCommand(Event e) {
        this.e = e;
    }

    @Override
    public void execute(Socket socket, ClientController clientController) {
        clientController.showUpdateForEvents(e);
    }
}
