package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.Socket.Client.SocketClient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class NumPlayersCommand implements ClientCommand {
    public NumPlayersCommand() {    }

    @Override
    public void execute(Socket socket, ClientController clientController) throws IOException {

    }
}
