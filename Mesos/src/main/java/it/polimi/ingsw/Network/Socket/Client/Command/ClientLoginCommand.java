package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Network.ClientController;
import it.polimi.ingsw.Network.ClientMain;
import it.polimi.ingsw.Network.Socket.Client.SocketClient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientLoginCommand implements ClientCommand{
    private  final String username = ClientMain.getUsername();
    private  final String[] totems;
    private String chosenOne;

    public ClientLoginCommand(String[] totems) {
        this.totems = totems;
    }

    /**
     * @param socket           :
     * @param clientController
     * @throws IOException:
     */
    @Override
    public void execute(Socket socket, ClientController clientController) throws IOException {
        String totems;
        if(this.totems!=null){
            totems = String.join(",", this.totems);
        }
        Scanner input = new Scanner(System.in);

        synchronized (SocketClient.inputLock){
            clientController.askForLogin();
            out.println("LOGIN#"+username);
            SocketClient.inputLock.notifyAll();
        }

    }
}
