package it.polimi.ingsw.Network.Socket.Client.Command;

import it.polimi.ingsw.Network.Socket.Client.ClientSocket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class NumPlayersCommand implements ClientCommand {
    private final String question;
    public NumPlayersCommand(String question) {
        this.question = question;
    }
    /**
     *
     */
    @Override
    public void execute(Socket socket) throws IOException {
        Scanner sc = new Scanner(System.in);
        int numPlayers;
        synchronized (ClientSocket.inputLock){
            System.out.print("Enter number of players that will join the game, from 2 to 5: ");
            numPlayers = sc.nextInt();
            while(numPlayers < 2 || numPlayers > 5) {
                System.out.println("Invalid input.");
                System.out.print("Enter number of players that will join the game, from 2 to 5: ");
                numPlayers = sc.nextInt();
            }
        }

        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
        out.println("SETNUMPLAYERS#"+ numPlayers);
    }
}
