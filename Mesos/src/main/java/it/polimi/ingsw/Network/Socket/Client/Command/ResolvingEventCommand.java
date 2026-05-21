package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Cards.Events.Event;
import it.polimi.ingsw.Network.ClientController;

import java.io.IOException;
import java.net.Socket;

public class ResolvingEventCommand implements ClientCommand {
    Event e ;
    public ResolvingEventCommand(Event e) {
        this.e = e;
    }

    @Override
    public void execute(Socket socket, ClientController clientController) throws IOException {
        //clientController.showUpdateForEvents(e);
    }
}
