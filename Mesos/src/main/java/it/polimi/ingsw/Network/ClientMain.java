package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.RMI.ClientRMI;
import it.polimi.ingsw.Network.RMI.VirtualServer;
import it.polimi.ingsw.Network.Socket.Client.SocketClient;
import it.polimi.ingsw.UI.TUI;

import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientMain {

    private static String username;
    private static GraphicInterface userInterface;

    public static String getUsername(){
        return ClientMain.username;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Welcome user");
        //System.out.print("Please write your username:");

        Scanner sc = new Scanner(System.in);
//        System.out.print("Select the preferred User Interface that you want to use [0:Textual/1:Graphic]:");
//        int UI = sc.nextInt();
//        while(!(UI == 1 || UI == 0) ){
//            System.out.println("Invalid input");
//            System.out.print("Select the preferred User Interface that you want to use [0:Textual/1:Graphic]:");
//            UI = sc.nextInt();
//        }

//        if(UI == 1){
//            //GUI
//        } else {
//            userInterface = new TUI();
//        }


//        ClientMain.username = sc.nextLine();
//        while(ClientMain.username.isEmpty()){
//            System.out.print("The username cannot be empty,  please try again:");
//            ClientMain.username = sc.nextLine();
//        }

        System.out.print("Please choose the preferred connection protocol (0:RMI/1:Socket) :");
        int connection = sc.nextInt();
        while(!(connection == 1 || connection == 0) ){
            System.out.println("Invalid input");
            System.out.print("Please choose the connection protocol (0:RMI/1:Socket) :");
            connection = sc.nextInt();
        }



        if(connection == 0){
            try {
                Registry registry = LocateRegistry.getRegistry("localhost", 1234);

                VirtualServer server = (VirtualServer) registry.lookup("---MESOS_SERVER---");

                ClientRMI client = new ClientRMI( new ClientController(new TUI()) );

                client.run();

            } catch (Exception e) {
                e.printStackTrace();
            }
            //gestione RMI
        }else if(connection == 1){
            //gestione Socket
            //controllo possibile server pieno
            SocketClient socketClient = new SocketClient(new Socket(), new ClientController(userInterface));
        }
    }
}
