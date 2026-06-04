package it.polimi.ingsw.Network;

import com.mysql.cj.xdevapi.Client;
import it.polimi.ingsw.UI.GUI.MainApp;
import it.polimi.ingsw.UI.TUI;
import javafx.application.Application;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientMain {

    private static String username;
    private static GraphicInterface userInterface;
    private static AtomicBoolean isTerminated = new AtomicBoolean(false);
    private static ClientController clientController;

    public static String getUsername(){
        return ClientMain.username;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Welcome user");
        //System.out.print("Please write your username:");

        Scanner sc = new Scanner(System.in);
        System.out.print("Select the preferred User Interface that you want to use [0:Textual/1:Graphic]:");
        int UI = sc.nextInt();
        while(!(UI == 1 || UI == 0) ){
            System.out.println("Invalid input");
            System.out.print("Select the preferred User Interface that you want to use [0:Textual/1:Graphic]:");
            UI = sc.nextInt();
        }

        clientController = new ClientController();
        if(UI == 1){
            MainApp.setController(clientController);
            Application.launch(MainApp.class);
        } else {
            userInterface = new TUI(clientController);
        }
    }

    public synchronized static void terminateClient(){
        if(isTerminated.get()){
            return;
        }
        isTerminated.set(true);
        clientController.close();
        System.out.println("Thank you for playing Mesos, we hope to see you again! :)");
        System.exit(0);
    }

}
