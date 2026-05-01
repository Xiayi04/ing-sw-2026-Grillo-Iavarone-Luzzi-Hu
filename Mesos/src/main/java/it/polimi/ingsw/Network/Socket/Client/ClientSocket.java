package it.polimi.ingsw.Network.Socket.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket implements Runnable{
    private Socket socket;
    public static final Object inputLock = new Object();
    public static final Object outputLock = new Object();
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


            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
