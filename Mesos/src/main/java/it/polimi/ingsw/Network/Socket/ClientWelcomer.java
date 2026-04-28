package it.polimi.ingsw.Network.Socket;

import java.net.Socket;
import java.util.Scanner;

public class ClientWelcomer {
    public static void main(String[] args){
        System.out.println("Welcome user");
        System.out.print("Please write your username:");

        Scanner sc = new Scanner(System.in);
        String username = sc.nextLine();

        System.out.print("Please choose the preferred connection protocol (0:RMI/1:Socket) :");
        int connection = sc.nextInt();
        while(!(connection != 1 || connection != 0) ){
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
