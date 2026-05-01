package it.polimi.ingsw.Network.Socket.Client.Command;

import java.io.IOException;
import java.net.Socket;

public interface ClientCommand {
    void execute(Socket socket) throws IOException;
}
