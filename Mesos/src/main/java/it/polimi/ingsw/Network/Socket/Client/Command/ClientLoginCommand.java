package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Network.ClientMain;
import it.polimi.ingsw.Network.Socket.Client.ClientSocket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class ClientLoginCommand implements ClientCommand{
    private  final String username = ClientMain.getUsername();
    private  final String[] totems;
    private String chosenOne;

    public ClientLoginCommand(String[] totems) {
        this.totems = totems;
    }

    /**
     * @param socket:
     * @throws IOException:
     */
    @Override
    public void execute(Socket socket) throws IOException {
        String totems = String.join(",", this.totems);
        Scanner input = new Scanner(System.in);

        synchronized (ClientSocket.inputLock){
            System.out.print("Please choose a totem's color among the available ones:"+ totems);

            chosenOne = input.nextLine();
            boolean flag = Arrays.stream(this.totems)
                                    .anyMatch(totem -> totem.equals(chosenOne.toLowerCase()));
            while(!flag) {
                System.out.print("");
                chosenOne = input.nextLine();
                flag = Arrays.stream(this.totems)
                                    .anyMatch(totem -> totem.equals(chosenOne.toLowerCase()));
            }

        }
        ClientSocket.inputLock.notifyAll();
        synchronized (ClientSocket.outputLock){
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("LOGIN#"+this.username+chosenOne);
        }
        ClientSocket.outputLock.notifyAll();
    }
}
