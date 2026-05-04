package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.Socket.Client.ClientSocket;

import java.net.Socket;
import java.util.Scanner;

public class ClientMain {

    private static String username;

    public static String getUsername(){
        return ClientMain.username;
    }

    public static void main(String[] args){
        System.out.println("Welcome user");
        System.out.print("Please write your username:");

        Scanner sc = new Scanner(System.in);
        ClientMain.username = sc.nextLine();
        while(ClientMain.username.isEmpty()){
            System.out.print("The username cannot be empty,  please try again:");
            ClientMain.username = sc.nextLine();
        }

        System.out.print("Please choose the preferred connection protocol (0:RMI/1:Socket) :");
        int connection = sc.nextInt();
        while(!(connection == 1 || connection == 0) ){
            System.out.println("Invalid input");
            System.out.print("Please choose the connection protocol (0:RMI/1:Socket) :");
            connection = sc.nextInt();
        }

        if(connection == 0){
            //gestione RMI
        }else if(connection == 1){
            //gestione Socket
            //controllo possibile server pieno
            new Thread(new ClientSocket(new Socket())).start();

        }
    }
}
