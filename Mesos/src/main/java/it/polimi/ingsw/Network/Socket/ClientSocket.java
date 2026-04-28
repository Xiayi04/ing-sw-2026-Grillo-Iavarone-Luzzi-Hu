package it.polimi.ingsw.Network.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket implements Runnable{
    private Socket socket;
    public ClientSocket(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        try {
            socket = new Socket("localhost", 1234);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            PrintWriter writer = new PrintWriter(
                    socket.getOutputStream(), true);

            //implementazione heartbeat

            while(true){
                /* ogni volta il server da un comando al client
                *
                */

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
